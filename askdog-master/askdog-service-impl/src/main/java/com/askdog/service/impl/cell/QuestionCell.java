package com.askdog.service.impl.cell;

import com.askdog.common.exception.CommonException;
import com.askdog.common.utils.QRCodeGenerator;
import com.askdog.model.data.Actions;
import com.askdog.model.data.Actions.QuestionVote;
import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.data.snapshot.QuestionSnapshot;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.Label;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.User;
import com.askdog.model.entity.builder.LabelBuilder;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.model.repository.LabelRepository;
import com.askdog.model.repository.QuestionRepository;
import com.askdog.model.repository.mongo.QuestionLocationRepository;
import com.askdog.model.repository.mongo.action.QuestionFollowRepository;
import com.askdog.model.repository.mongo.action.QuestionShareRepository;
import com.askdog.model.repository.mongo.action.QuestionViewRepository;
import com.askdog.model.repository.mongo.action.QuestionVoteRepository;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.AnswerInteraction;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.bo.QuestionInteraction;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.exception.UtilException;
import com.askdog.service.impl.configuration.QuestionWeightConfiguration;
import com.askdog.service.impl.configuration.WebConfiguration.QuestionConfiguration;
import com.askdog.service.event.annotation.TriggerEvent;
import com.askdog.service.snapshot.QuestionSnapshotService;
import com.askdog.service.storage.Provider;
import com.askdog.service.storage.StorageAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.askdog.common.utils.Conditions.checkState;
import static com.askdog.common.utils.Variables.variables;
import static com.askdog.model.data.builder.QuestionFollowBuilder.questionFollowBuilder;
import static com.askdog.model.data.inner.ResourceType.QUESTION_QR_CODE;
import static com.askdog.model.data.inner.StorageProvider.ALI_OSS;
import static com.askdog.model.data.inner.VoteDirection.DOWN;
import static com.askdog.model.data.inner.VoteDirection.UP;
import static com.askdog.model.data.snapshot.CommentSnapshot.CommentType.QUESTION_COMMENT;
import static com.askdog.model.entity.inner.EventType.FOLLOW_QUESTION;
import static com.askdog.model.entity.inner.EventType.UN_FOLLOW_QUESTION;
import static com.askdog.service.exception.NotFoundException.Error.QUESTION;
import static com.askdog.service.exception.UtilException.Error.QUESTION_QR_GENERATE_FAILED;
import static com.google.common.collect.ImmutableMap.of;
import static java.lang.Math.log;
import static java.util.stream.Collectors.toList;

@Component
@Transactional
public class QuestionCell {

    @Autowired private QuestionRepository questionRepository;
    @Autowired private QuestionConfiguration questionConfiguration;
    @Autowired private QuestionFollowRepository questionFollowRepository;
    @Autowired private QuestionShareRepository questionShareRepository;
    @Autowired private QuestionVoteRepository questionVoteRepository;
    @Autowired private QuestionViewRepository questionViewRepository;
    @Autowired private QuestionLocationRepository questionLocationRepository;
    @Autowired private QuestionSnapshotService questionSnapshotService;
    @Autowired private LabelRepository labelRepository;
    @Autowired private QuestionWeightConfiguration questionWeightConfiguration;

    @Autowired private AnswerCell answerCell;
    @Autowired private AnswerRepository answerRepository;

    @Autowired private UserCell userCell;
    @Autowired private CommentCell commentCell;

    @Provider(ALI_OSS)
    @Autowired private StorageAgent storageAgent;

    public StorageRecord generateQRCode(@Nonnull String questionId) throws ServiceException {
        return storageAgent.save(QUESTION_QR_CODE, fileName(questionId), qrCode(questionId));
    }

    @Nonnull
    public String generateQRLink(@Nonnull String questionId) throws ServiceException {
        return storageAgent.resourceURL(QUESTION_QR_CODE, fileName(questionId));
    }

    public boolean isUserFollowTheQuestion(@Nonnull String userId, @Nonnull String questionId) {
        return questionFollowRepository.countByUserAndTarget(userId, questionId) != 0;
    }

    public boolean isUserUpVoteTheQuestion(@Nonnull String userId, @Nonnull String questionId) {
        Optional<QuestionVote> questionVoteOptional = questionVoteRepository.findByUserAndTarget(userId, questionId);
        return questionVoteOptional.isPresent() && questionVoteOptional.get().getDirection().equals(UP);
    }

    public Optional<AnswerInteraction> findBestAnswer(@Nonnull String questionId) {
        return answerRepository.findAllByQuestionUuid(questionId).stream().map(toAnswerInteraction())
                .max(AnswerInteraction::compareTo);
    }

    public Optional<AnswerDetail> findLatestAnswer(@Nonnull String questionId) {
        return answerRepository.findTopByQuestionUuidOrderByCreationTimeDesc(questionId).map(answer -> {
            try {
                return answerCell.findDetailStateLess(answer.getUuid());

            } catch (ServiceException e) {
                throw new RuntimeException("Cannot find answer detail [id=" + answer.getUuid() + "]", e);
            }
        });
    }

    public QuestionInteraction getQuestionInteraction(@Nonnull String questionId) {
        QuestionInteraction questionInteraction = new QuestionInteraction();
        questionInteraction.setViewCount(questionViewRepository.countByTarget(questionId));
        questionInteraction.setFollowCount(questionFollowRepository.countByTarget(questionId));
        questionInteraction.setUpVoteCount(questionVoteRepository.countByTargetAndDirection(questionId, UP));
        questionInteraction.setDownVoteCount(questionVoteRepository.countByTargetAndDirection(questionId, DOWN));
        questionInteraction.setViewCount(questionViewRepository.countByTarget(questionId));
        questionInteraction.setFollowCount(questionFollowRepository.countByTarget(questionId));
        questionInteraction.setShareCount(questionShareRepository.countByTarget(questionId));
        return questionInteraction;
    }

    public Question findExists(@Nonnull String questionId) throws NotFoundException {
        return questionRepository.findByUuid(questionId).orElseThrow(() -> new NotFoundException(QUESTION));
    }

    public Optional<Question> find(@Nonnull String questionId) {
        return questionRepository.findByUuid(questionId);
    }

    @Cacheable(value = "cache:cell:question:detail:stateless", key = "#questionId")
    public QuestionDetail findDetailStateLess(@Nonnull String questionId) throws ServiceException {
        // TODO impl findDetailStateLess
        return findDetail("", questionId, true);
    }

    @Cacheable(value = "cache:cell:snapshot:question:detail:stateless", key = "#questionId")
    public QuestionDetail findDetailStateLessElseSnapshot(@Nonnull String questionId) throws ServiceException {
        // TODO impl findDetailStateLess
        return findDetailElseSnapshot("", questionId, true);
    }

    @Cacheable(value = "cache:cell:question:detail", key = "#userId + ':' + #questionId + ':' + #fetchAnswerDetail")
    public QuestionDetail findDetail(@Nonnull String userId, @Nonnull String questionId, boolean fetchAnswerDetail) throws ServiceException {
        Question savedQuestion = findExists(questionId);
        QuestionDetail questionDetail = new QuestionDetail().from(savedQuestion);
        // questionDetail.setQrLink(generateQRLink(questionId));
        questionDetail.setAuthor(userCell.findDetail(savedQuestion.getAuthor().getUuid())); // todo user snapshot

        questionDetail.setCommentCount(savedQuestion.getComments() == null ? 0 : savedQuestion.getComments().size());
        questionDetail.setComments(commentCell.parseCommentDetail(savedQuestion.getUuid(), userId, savedQuestion.getComments()));

        questionLocationRepository.findByTarget(questionId).ifPresent(questionLocation ->
                questionDetail.setLocation(questionLocation.getDescription().addressRecommend())
        );

        questionDetail.setAnswerCount(savedQuestion.getAnswers() == null ? 0 : savedQuestion.getAnswers().size());
        if (fetchAnswerDetail && savedQuestion.getAnswers() != null) {
            List<AnswerDetail> answerDetails = answerCell.findDetailByIds(savedQuestion.getAnswers().stream().map(Answer::getUuid).collect(Collectors.toList()), userId, false);

            answerDetails.sort((o1, o2) -> {
                double o1Score = answerCell.calculateScore(o1);
                double o2Score = answerCell.calculateScore(o2);
                return o1Score > o2Score ? -1 : o1Score == o2Score ? 0 : 1;
            });

            questionDetail.setAnswers(answerDetails);
        }

        Optional<AnswerInteraction> bestAnswer = findBestAnswer(questionDetail.getUuid());
        if (bestAnswer.isPresent()) {
            questionDetail.setBestAnswer(answerCell.findDetail(userId, bestAnswer.get().getAnswer().getUuid(), false));
        }

        // Order is important here, do NOT modify, initState must after answers initialization
        initCount(questionDetail);
        initState(questionDetail, userId);

        return questionDetail;
    }

    @Cacheable(value = "cache:cell:snapshot:question:detail", key = "#userId + ':' + #questionId + ':' + #fetchAnswerDetail")
    public QuestionDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String questionId, boolean fetchAnswerDetail) throws ServiceException {
        if (checkExists(questionId)) {
            return findDetail(userId, questionId, fetchAnswerDetail);
        }

        QuestionSnapshot questionSnapshot = questionSnapshotService.findByQuestionId(questionId);

        QuestionDetail questionDetail = new QuestionDetail();
        questionDetail.setUuid(questionSnapshot.getQuestionId());
        questionDetail.setSubject(questionSnapshot.getSubject());
        questionDetail.setContent(questionSnapshot.getContent());
        questionDetail.setAuthor(userCell.findDetail(questionSnapshot.getAuthorId())); // todo user snapshot
        questionDetail.setAnonymous(questionSnapshot.isAnonymous());
        questionDetail.setExperience(questionSnapshot.isShare());
        questionDetail.setLanguage(questionSnapshot.getLanguage());
        questionDetail.setLocation(questionSnapshot.getLocation());
        questionDetail.setCreationTime(questionSnapshot.getCreationTime());
        questionDetail.setLabels(questionSnapshot.getLabelNames());
        // questionDetail.setQrLink(questionSnapshot.getQrLink());
        questionDetail.setViewCount(questionSnapshot.getViewCount());
        questionDetail.setFollowCount(questionSnapshot.getFollowCount());
        questionDetail.setUpVoteCount(questionSnapshot.getUpVoteCount());
        questionDetail.setDownVoteCount(questionSnapshot.getDownVoteCount());
        questionDetail.setDeleted(true);

        questionDetail.setComments(commentCell.findDetailElseSnapshotByIds(QUESTION_COMMENT, questionSnapshot.getCommentIds(), userId));
        questionDetail.setCommentCount(questionSnapshot.getCommentIds() == null ? 0 : questionSnapshot.getCommentIds().size());

        questionDetail.setAnswerCount(questionSnapshot.getAnswerIds() == null ? 0 : questionSnapshot.getAnswerIds().size());
        if (fetchAnswerDetail && questionSnapshot.getAnswerIds() != null) {
            List<AnswerDetail> answerDetails = answerCell.findDetailElseSnapshotByIds(questionSnapshot.getAnswerIds(), userId, false);

            answerDetails.sort((o1, o2) -> {
                double o1Score = answerCell.calculateScore(o1);
                double o2Score = answerCell.calculateScore(o2);
                return o1Score > o2Score ? -1 : o1Score == o2Score ? 0 : 1;
            });

            questionDetail.setAnswers(answerDetails);
        }

        if (!StringUtils.isEmpty(questionSnapshot.getBestAnswerId())) {
            questionDetail.setBestAnswer(answerCell.findDetailElseSnapshot(userId, questionSnapshot.getBestAnswerId(), false));
        }

        // Order is important here, do NOT modify, initState must be behind answers initialization
        // initCount(questionDetail);
        initState(questionDetail, userId);

        return questionDetail;
    }

    private QuestionDetail initCount(@Nonnull QuestionDetail questionDetail) throws ServiceException {
        QuestionInteraction questionInteraction = getQuestionInteraction(questionDetail.getUuid());
        questionDetail.setViewCount(questionInteraction.getViewCount());
        questionDetail.setFollowCount(questionInteraction.getFollowCount());
        questionDetail.setUpVoteCount(questionInteraction.getUpVoteCount());
        questionDetail.setDownVoteCount(questionInteraction.getDownVoteCount());
        return questionDetail;
    }

    private QuestionDetail initState(@Nonnull QuestionDetail questionDetail, @Nonnull String userId) throws ServiceException {
        Optional<QuestionVote> foundQuestionVote = questionVoteRepository.findByUserAndTarget(userId, questionDetail.getUuid());
        if (foundQuestionVote.isPresent()) {
            questionDetail.setUpVoted(foundQuestionVote.get().getDirection().equals(VoteDirection.UP));
            questionDetail.setDownVoted(foundQuestionVote.get().getDirection().equals(VoteDirection.DOWN));
        }

        questionDetail.setFollowed(isUserFollowTheQuestion(userId, questionDetail.getUuid()));
        questionDetail.setMine(questionDetail.getAuthor().getUuid().equals(userId));
        questionDetail.setDeletable(isDeletable(userId, questionDetail.getUuid()));

        List<AnswerDetail> answerDetails = questionDetail.getAnswers();
        if (answerDetails != null && answerDetails.size() > 0) {
            for (AnswerDetail answerDetail : answerDetails) {
                if (answerDetail.getAnswerer().getUuid().equals(userId)) {
                    questionDetail.setAnswered(true);
                    break;
                }
            }
        }

        return questionDetail;
    }

    public boolean isDeletable(@Nonnull String userId, @Nonnull String questionId) throws NotFoundException {
        if (!checkExists(questionId)) {
            return false;
        }

        Question question = findExists(questionId);

        if (!question.getAuthor().getUuid().equals(userId)) {
            return false;
        }

        List<Answer> answerList = question.getAnswers();
        if (answerList != null && answerList.size() > 0) {
            for (Answer answer : answerList) {
                if (!answer.getAnswerer().getUuid().equals(userId)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void forEachFollowers(String questionId, Consumer<User> sender) {
        userCell.find(questionFollowRepository.findAllByTarget(questionId).stream()
                .map(Actions.Action::getUser).collect(toList())).stream()
                .forEach(sender);
    }

    private String fileName(String questionId) {
        return questionId + "." + questionConfiguration.getQrCode().getFormat();
    }

    private InputStream qrCode(String questionId) throws ServiceException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String detailUrl = variables(of("questionId", questionId)).pattern("\\{(\\w+)\\}").replace(questionConfiguration.getDetailUrl());
        try {
            QuestionConfiguration.QrCode qrCode = questionConfiguration.getQrCode();
            QRCodeGenerator.generate(detailUrl, qrCode.getWidth(), qrCode.getHeight()).writeToStream(qrCode.getFormat(), outputStream);
        } catch (CommonException e) {
            throw new UtilException(QUESTION_QR_GENERATE_FAILED, e);
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private Function<Answer, AnswerInteraction> toAnswerInteraction() {
        return answer -> {
            AnswerInteraction answerInteraction = new AnswerInteraction();
            answerInteraction.setAnswer(answer);
            answerCell.fill(answerInteraction);
            return answerInteraction;
        };
    }

    @TriggerEvent(@TriggerEvent.TriggerEventItem(performerId = "userId", eventType = FOLLOW_QUESTION, targetId = "questionId"))
    public void follow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        if (questionFollowRepository.countByUserAndTarget(userId, questionId) == 0) {
            checkQuestionId(questionId);
            userCell.checkUserId(userId);
            Actions.QuestionFollow questionFollow = questionFollowBuilder()
                    .user(userId)
                    .question(questionId)
                    .build();
            questionFollowRepository.save(questionFollow);
        }
    }

    @TriggerEvent(@TriggerEvent.TriggerEventItem(performerId = "userId", eventType = UN_FOLLOW_QUESTION, targetId = "questionId"))
    public void unfollow(@Nonnull String userId, @Nonnull String questionId) throws ServiceException {
        questionFollowRepository.deleteByUserAndTarget(userId, questionId);
    }

    public void checkQuestionId(String questionId) throws NotFoundException {
        checkState(questionRepository.exists(questionId), new NotFoundException(QUESTION));
    }

    public boolean checkExists(String questionId) {
        return questionRepository.exists(questionId);
    }


    public double calculateScore(QuestionDetail questionDetail) {
        long viewCountEntropy = questionDetail.getViewCount() * questionWeightConfiguration.getViewCount();
        long followCountEntropy = questionDetail.getFollowCount() * questionWeightConfiguration.getFollowCount();
        long upVoteCountEntropy = questionDetail.getUpVoteCount() * questionWeightConfiguration.getUpVoteCount();
        long downVoteCountEntropy = questionDetail.getDownVoteCount() * questionWeightConfiguration.getDownVoteCount();
        long answerCountEntropy = questionDetail.getAnswerCount() * questionWeightConfiguration.getAnswerCount();
        long commentCountEntropy = questionDetail.getCommentCount() * questionWeightConfiguration.getCommentCount();

        long weight = viewCountEntropy + followCountEntropy + upVoteCountEntropy - downVoteCountEntropy + answerCountEntropy + commentCountEntropy;

        if (weight == 0) return 0;
        return weight > 0 ? log(weight) : -log(weight);
    }

    public List<Label> getLabels(Set<String> labelNames) {
        if (labelNames == null || labelNames.size() == 0) {
            return null;
        }
        return labelNames.parallelStream()
                .map(labelName -> labelRepository.findByName(labelName).orElse(LabelBuilder.build(labelName)))
                .filter(label -> label != null)
                .collect(toList());
    }

}
