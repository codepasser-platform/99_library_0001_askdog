package com.askdog.service.impl.incentive;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.IncentiveLog;
import com.askdog.model.data.builder.IncentiveLogBuilder;
import com.askdog.model.data.inner.NotificationType;
import com.askdog.model.entity.IncentivePolicy;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.model.repository.IncentivePolicyRepository;
import com.askdog.model.repository.QuestionRepository;
import com.askdog.model.repository.UserRepository;
import com.askdog.model.repository.mongo.IncentiveLogRepository;
import com.askdog.model.repository.mongo.action.*;
import com.askdog.service.IncentiveLogService;
import com.askdog.service.exception.ListenerException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.event.ListenerRegistry;
import com.askdog.service.notification.Notifier;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static com.askdog.model.data.builder.OriginalNotificationBuilder.notificationBuilder;
import static com.askdog.model.data.inner.VoteDirection.DOWN;
import static com.askdog.model.data.inner.VoteDirection.UP;
import static com.askdog.model.entity.inner.EventType.LOGIN;
import static com.askdog.model.entity.inner.IncentiveType.POINTS;

@Component
public class IncentiveListenerRegistry {

    private static final Logger logger = LoggerFactory.getLogger(IncentiveListenerRegistry.class);

    @Autowired private UserRepository userRepository;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private ListenerRegistry listenerRegistry;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private ExpressionResolverFactory expressionResolverFactory;
    @Autowired private IncentiveLogRepository incentiveLogRepository;
    @Autowired private IncentivePolicyRepository incentivePolicyRepository;
    @Autowired private IncentivePolicyProcessorFactory incentivePolicyProcessorFactory;

    @Autowired private AnswerVoteRepository answerVoteRepository;
    @Autowired private QuestionVoteRepository questionVoteRepository;

    @Autowired private QuestionViewRepository questionViewRepository;
    @Autowired private QuestionFollowRepository questionFollowRepository;
    @Autowired private AnswerFavoriteRepository answerFavoriteRepository;
    @Autowired private IncentiveLogService incentiveLogService;

    @Autowired private Notifier notifier;

    @PostConstruct
    private void init() throws ListenerException {
        for (IncentivePolicy incentivePolicy : incentivePolicyRepository.findAll()) {
            Consumer<EventLog> eventConsumer = eventLog -> {
                try {

                    if (eventLog.getEventType().equals(LOGIN)) {
                        Optional<IncentiveLog> incentiveLogOptional = incentiveLogService.findLastLoginLog(eventLog.getPerformerId(), POINTS, IncentiveReason.LOGIN);
                        if (incentiveLogOptional.isPresent()) {
                            Date createTime = incentiveLogOptional.get().getCreateTime();
                            LocalDate dateNow = LocalDate.now();
                            LocalDate dateLog = createTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            if (dateNow.isEqual(dateLog) || dateNow.isBefore(dateLog)) {
                                return;
                            }
                        }
                    }

                    // process incentive policy
                    ExpressionResolver expressionResolver = expressionResolverFactory.get(incentivePolicy.getExpressionType());
                    IncentivePolicyProcessor policyProcessor = incentivePolicyProcessorFactory.get(incentivePolicy.getIncentiveType());
                    Map<String, Object> metadata = prepareExpressionParams(incentivePolicy, eventLog);
                    int incentiveValue = expressionResolver.resolver(metadata, incentivePolicy.getExpression());
                    User targetUser = getTargetUser(incentivePolicy, eventLog);

                    if (targetUser != null) {
                        policyProcessor.process(targetUser, incentiveValue);

                        // record incentive action
                        if (incentiveValue != 0) {
                            IncentiveLog incentiveLog = incentiveLogRepository.save(IncentiveLogBuilder.getBuilder()
                                    .setEventLogId(eventLog.getId())
                                    .setIncentiveOwnerId(targetUser.getUuid())
                                    .setIncentiveReason(incentivePolicy.getIncentiveReason())
                                    .setIncentiveType(incentivePolicy.getIncentiveType())
                                    .setIncentiveMetadata(metadata)
                                    .setIncentiveValue(incentiveValue).build());

                            notifier.notify(notificationBuilder()
                                    .notificationType(NotificationType.INCENTIVE)
                                    .recipientUserId(targetUser.getUuid())
                                    .refLogId(incentiveLog.getId())
                                    .build());
                        }
                    }

                } catch (ServiceException e) {
                    logger.error("Cannot process incentive policy.", e);
                }
            };

            listenerRegistry.register(
                    eventConsumer::accept,
                    incentivePolicy.getEventType(),
                    incentivePolicy.getName(),
                    incentivePolicy.getTriggerNames()
            );
        }
    }

    private Map<String, Object> prepareExpressionParams(IncentivePolicy incentivePolicy, EventLog event) {
        Map<String, Object> params = Maps.newHashMap();
        switch (incentivePolicy.getIncentiveReason()) {
            case QUESTION_BEEN_FOLLOWED:
                params.put("question_follow_count", questionFollowRepository.countByTarget(event.getTargetId()));
                break;
            case QUESTION_BEEN_VIEWED:
                params.put("question_view_count", questionViewRepository.countByTarget(event.getTargetId()));
                break;
            case QUESTION_BEEN_UP_VOTED:
                params.put("question_up_vote_count", questionVoteRepository.countByTargetAndDirection(event.getTargetId(), UP));
                break;
            case QUESTION_BEEN_DOWN_VOTED:
                params.put("question_down_vote_count", questionVoteRepository.countByTargetAndDirection(event.getTargetId(), DOWN));
                break;
            case ANSWER_BEEN_UP_VOTED:
                params.put("answer_up_vote_count", answerVoteRepository.countByTargetAndDirection(event.getTargetId(), UP));
                break;
            case ANSWER_BEEN_DOWN_VOTED:
                params.put("answer_down_vote_count", answerVoteRepository.countByTargetAndDirection(event.getTargetId(), DOWN));
                break;
            case ANSWER_BEEN_FAVORITED:
                params.put("answer_fav_count", answerFavoriteRepository.countByTarget(event.getTargetId()));
                break;
        }
        return params;
    }

    private User getTargetUser(IncentivePolicy incentivePolicy, EventLog eventLog) throws ServiceException {
        switch (incentivePolicy.getIncentiveReason()) {
            case QUESTION_BEEN_UP_VOTED:
            case QUESTION_BEEN_VIEWED:
            case QUESTION_BEEN_FOLLOWED:
            case QUESTION_BEEN_DOWN_VOTED:
                return questionRepository.findOne(eventLog.getTargetId()).getAuthor();
            case ANSWER_BEEN_UP_VOTED:
            case ANSWER_BEEN_DOWN_VOTED:
            case ANSWER_BEEN_FAVORITED:
                return answerRepository.findOne(eventLog.getTargetId()).getAnswerer();
            default:
                return userRepository.findByUuid(eventLog.getPerformerId()).orElse(null);
        }
    }
}