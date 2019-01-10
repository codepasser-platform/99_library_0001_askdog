package com.askdog.service.impl;

import com.askdog.model.data.Actions.QuestionView;
import com.askdog.model.data.Actions.QuestionVote;
import com.askdog.model.data.ShareLog;
import com.askdog.model.data.builder.ShareLogBuilder;
import com.askdog.model.data.inner.ShareType;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.QuestionComment;
import com.askdog.model.entity.builder.AnswerBuilder;
import com.askdog.model.entity.builder.QuestionBuilder;
import com.askdog.model.entity.inner.State;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.model.repository.QuestionRepository;
import com.askdog.model.repository.mongo.ShareLogRepository;
import com.askdog.model.repository.mongo.action.QuestionViewRepository;
import com.askdog.model.repository.mongo.action.QuestionVoteRepository;
import com.askdog.service.AnswerService;
import com.askdog.service.QuestionCommentService;
import com.askdog.service.QuestionService;
import com.askdog.service.bo.*;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.NotFoundException.Error;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.annotation.EvictQuestionCache;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.service.impl.cell.ReportCell;
import com.askdog.service.impl.cell.UserCell;
import com.askdog.service.location.LocationAgent;
import com.askdog.service.location.Provider;
import com.askdog.service.snapshot.QuestionSnapshotService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import static com.askdog.model.data.builder.ViewBuilders.questionViewBuilder;
import static com.askdog.model.data.builder.VoteBuilders.questionVoteBuilder;
import static com.askdog.model.data.inner.TargetType.QUESTION;
import static com.askdog.model.data.inner.VoteDirection.DOWN;
import static com.askdog.model.data.inner.VoteDirection.UP;
import static com.askdog.model.data.inner.location.LocationProvider.TENCENT_MAP;
import static com.askdog.service.exception.ForbiddenException.Error.DELETE_QUESTION;
import static com.askdog.service.utils.PageUtils.rePage;

@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class QuestionServiceImpl implements QuestionService {

    @Autowired private UserCell userCell;
    @Autowired private ReportCell reportCell;
    @Autowired private AnswerCell answerCell;
    @Autowired private AnswerService answerService;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private QuestionCell questionCell;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private QuestionVoteRepository questionVoteRepository;
    @Autowired private QuestionViewRepository questionViewRepository;
    @Autowired private QuestionSnapshotService questionSnapshotService;
    @Autowired private QuestionCommentService questionCommentService;
    @Autowired private ShareLogRepository shareLogRepository;

    @Provider(TENCENT_MAP)
    @Autowired private LocationAgent locationAgent;

    @Override
    @Nonnull
    public Question find(@Nonnull String questionId) throws ServiceException {
        return questionRepository.findByUuid(questionId).orElseThrow(() -> new NotFoundException(Error.QUESTION));
    }

    @Nonnull
    @Override
    public QuestionDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String questionId, boolean fetchAnswerDetail) throws ServiceException {
        return questionCell.findDetailElseSnapshot(userId, questionId, fetchAnswerDetail);
    }

    @Override
    @Nonnull
    public String create(@Nonnull String userId, @Nonnull PureQuestion pureQuestion) throws ServiceException {
        Question question = QuestionBuilder.questionBuilder()
                .subject(pureQuestion.getSubject())
                .content(pureQuestion.getContent())
                .author(userId)
                .anonymous(pureQuestion.isAnonymous())
                .setShare(pureQuestion.getShareAnswer() != null)
                .state(State.OPEN)
                .language(Locale.SIMPLIFIED_CHINESE)
                .creationTime(new Date()).build();

        question.setLabels(questionCell.getLabels(pureQuestion.getLabels()));

        questionCell.generateQRCode(question.getUuid());

        // TODO for the new created questions, there is no location information. will retrieve the user's location only when user click the "share to nearby users".
        analysisLocation(question.getUuid(), pureQuestion);

        if (pureQuestion.getShareAnswer() != null) {
            Answer answer = AnswerBuilder.answerBuilder()
                    .anonymous(pureQuestion.isAnonymous())
                    .answerer(userId)
                    .content(pureQuestion.getShareAnswer().getContent())
                    .language(Locale.SIMPLIFIED_CHINESE)
                    .creationTime(new Date())
                    .question(question.getUuid()).build();
            question.setAnswers(Lists.newArrayList(answer));
        }

        Question savedQuestion = questionRepository.save(question);

        if (pureQuestion.isFollow()) {
            questionCell.follow(userId, savedQuestion.getUuid());
        }

        return savedQuestion.getUuid();
    }

    @Override
    @EvictQuestionCache
    public void delete(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        Question question = questionRepository.findByAuthor_UuidAndUuid(userId, questionId).orElseThrow(() -> new NotFoundException(Error.QUESTION));
        if (!questionCell.isDeletable(userId, question.getUuid())) {
            throw new ForbiddenException(DELETE_QUESTION);
        }

        questionSnapshotService.save(questionCell.findDetailStateLess(question.getUuid()));

        for (Answer answer : question.getAnswers()) {
            answerService.delete(userId, questionId, answer.getUuid());
        }

        for (QuestionComment questionComment : question.getComments()) {
            questionCommentService.delete(userId, questionId, questionComment.getUuid());
        }

        questionRepository.delete(question.getUuid());
    }

    @Override
    @EvictQuestionCache
    public void update(@Nonnull String userId, @Nonnull String questionId, @Nonnull AmendedQuestion amendedQuestion) throws ServiceException {
        Question question = questionRepository.findByAuthor_UuidAndUuid(userId, questionId).orElseThrow(() -> new NotFoundException(Error.QUESTION));
        question.setSubject(amendedQuestion.getSubject());
        question.setContent(amendedQuestion.getContent());
        question.setAnonymous(amendedQuestion.isAnonymous());
        question.setLabels(questionCell.getLabels(amendedQuestion.getLabels()));

        questionRepository.save(question);

        if (question.isExperience()) {
            Assert.notNull(amendedQuestion.getShareAnswer());
            Answer sharedAnswer = answerCell.findExists(amendedQuestion.getShareAnswer().getId());
            sharedAnswer.setContent(amendedQuestion.getShareAnswer().getContent());
            answerCell.save(sharedAnswer);
        }

        if (amendedQuestion.isFollow()) {
            questionCell.follow(userId, questionId);

        } else {
            questionCell.unfollow(userId, questionId);
        }
    }

    @Nonnull
    @Override
    public QuestionDetail findDetail(String userId, @Nonnull String questionId) throws ServiceException {
        return questionCell.findDetail(userId, questionId, true);
    }

    @Nonnull
    @Override
    public Optional<AnswerDetail> findLatestAnswer(@Nonnull String questionId) {
        return questionCell.findLatestAnswer(questionId);
    }

    @Nonnull
    @Override
    public QuestionInteraction interaction(@Nonnull String questionId) {
        return questionCell.getQuestionInteraction(questionId);
    }

    @Override
    public void view(String userId, @Nonnull String questionId) throws ServiceException {
        questionCell.checkQuestionId(questionId);
        userCell.checkUserId(userId, true);
        QuestionView questionView = questionViewBuilder()
                .user(userId)
                .target(questionId)
                .targetType(QUESTION)
                .build();
        questionViewRepository.save(questionView);
    }

    @Override
    @EvictQuestionCache
    public void follow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        questionCell.follow(userId, questionId);
    }

    @Override
    @EvictQuestionCache
    public void unfollow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        questionCell.unfollow(userId, questionId);
    }

    @Override
    public ShareLog share(@Nonnull String userId, @Nonnull String questionId, @Nonnull Double lat, @Nonnull Double lng) throws ServiceException {
        return shareLogRepository.save(ShareLogBuilder.builder().userId(userId).shareType(ShareType.QUESTION).targetId(questionId).geo(new ShareLog.Geo().setLat(lat).setLng(lng)).build());
    }

    @Override
    public void report(String userId, @Nonnull String questionId, @Nonnull PureReport pureReport) throws ServiceException {
        userCell.checkUserId(userId, true);
        questionCell.checkQuestionId(questionId);
        reportCell.report(userId, QUESTION, questionId, pureReport);
    }

    @Override
    @EvictQuestionCache
    public VoteCount vote(@Nonnull String userId, @Nonnull String questionId, @Nonnull VoteDirection direction) throws ServiceException {
        userCell.checkUserId(userId);
        questionCell.checkQuestionId(questionId);
        Optional<QuestionVote> foundQuestionVote = questionVoteRepository.findByUserAndTarget(userId, questionId);
        if (!foundQuestionVote.isPresent()) {
            questionVoteRepository.save(
                    questionVoteBuilder()
                            .user(userId)
                            .target(questionId)
                            .targetType(QUESTION)
                            .direction(direction)
                            .build());

        } else if (foundQuestionVote.get().getDirection() != direction) {
            QuestionVote questionVote = foundQuestionVote.get();
            questionVote.setDirection(direction);
            questionVote.setTime(new Date());
            questionVoteRepository.save(questionVote);
        }

        VoteCount voteCount = new VoteCount();
        voteCount.setUpVoteCount(questionVoteRepository.countByTargetAndDirection(questionId, UP));
        voteCount.setDownVoteCount(questionVoteRepository.countByTargetAndDirection(questionId, DOWN));
        return voteCount;
    }

    @Override
    @EvictQuestionCache
    public VoteCount unvote(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        userCell.checkUserId(userId);
        questionCell.checkQuestionId(questionId);
        questionVoteRepository.deleteByUserAndTarget(userId, questionId);

        VoteCount voteCount = new VoteCount();
        voteCount.setUpVoteCount(questionVoteRepository.countByTargetAndDirection(questionId, UP));
        voteCount.setDownVoteCount(questionVoteRepository.countByTargetAndDirection(questionId, DOWN));
        return voteCount;
    }

    @Nonnull
    @Override
    public Page<AnswerDetail> findAnswers(@Nonnull String questionId, @Nonnull Pageable paging) throws ServiceException {
        // TODO sort
        return rePage(answerRepository.findByQuestionUuid(questionId, paging), paging, t -> answerCell.findDetailStateLess(t.getUuid()));
    }

    private void analysisLocation(String questionId, PureQuestion pureQuestion) throws ServiceException {
        PureQuestion.Location location = pureQuestion.getLocation();
        Double lat = location == null ? null : location.getLat();
        Double lng = location == null ? null : location.getLng();
        locationAgent.analysis(QUESTION, questionId, pureQuestion.getIp(), lat, lng);
    }
}
