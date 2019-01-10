package com.askdog.service.impl.cell;

import com.askdog.model.data.Actions.AnswerVote;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.data.snapshot.AnswerSnapshot;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.AnswerComment;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.StorageLink;
import com.askdog.model.entity.builder.AnswerBuilder;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.PicContent;
import com.askdog.model.entity.inner.Type;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.model.repository.StorageLinkRepository;
import com.askdog.model.repository.mongo.action.AnswerFavoriteRepository;
import com.askdog.model.repository.mongo.action.AnswerShareRepository;
import com.askdog.model.repository.mongo.action.AnswerVoteRepository;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.AnswerInteraction;
import com.askdog.service.bo.PureAnswer;
import com.askdog.service.exception.ConflictException;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.configuration.AnswerWeightConfiguration;
import com.askdog.service.snapshot.AnswerSnapshotService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static com.askdog.common.utils.Conditions.checkState;
import static com.askdog.model.data.snapshot.CommentSnapshot.CommentType.ANSWER_COMMENT;
import static com.askdog.service.exception.ConflictException.Error.ALREADY_ANSWERED;
import static com.askdog.service.exception.ForbiddenException.Error.DELETE_ANSWER;
import static com.askdog.service.exception.NotFoundException.Error.ANSWER;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Math.log;
import static java.util.Locale.SIMPLIFIED_CHINESE;

@Component
@Transactional
public class AnswerCell {

    @Autowired private UserCell userCell;
    @Autowired private CommentCell commentCell;
    @Autowired private QuestionCell questionCell;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private AnswerVoteRepository answerVoteRepository;
    @Autowired private AnswerShareRepository answerShareRepository;
    @Autowired private AnswerFavoriteRepository answerFavoriteRepository;
    @Autowired private AnswerWeightConfiguration answerWeightConfiguration;
    @Autowired private StorageLinkRepository storageLinkRepository;
    @Autowired private AnswerSnapshotService answerSnapshotService;

    public AnswerDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull PureAnswer pureAnswer) throws ServiceException {
        questionCell.checkQuestionId(questionId);
        userCell.checkUserId(userId);

        Answer answer = AnswerBuilder.answerBuilder()
                .answerer(userId)
                .anonymous(pureAnswer.isAnonymous())
                .question(questionId)
                .language(SIMPLIFIED_CHINESE)
                .creationTime(new Date())
                .build();

        updatePictureStatus(answer, pureAnswer.getContent());
        Answer savedAnswer = save(answer);

        if (pureAnswer.isFollow()) {
            questionCell.follow(userId, questionId);

        } else {
            questionCell.unfollow(userId, questionId);
        }

        return findDetail(userId, savedAnswer.getUuid(), true);
    }

    public Answer save(Answer answer) throws ServiceException {
        Optional<Answer> answerOptional = answerRepository.findByAnswererUuidAndQuestionUuid(answer.getAnswerer().getUuid(), answer.getQuestion().getUuid());
        if (answerOptional.isPresent() && !answerOptional.get().getUuid().equals(answer.getUuid())) {
            throw new ConflictException(ALREADY_ANSWERED);
        }

        return answerRepository.save(answer);
    }

    public void delete(@Nonnull String userId, @Nonnull String answerId) throws ServiceException {
        checkState(isDeletable(userId, answerId), new ForbiddenException(DELETE_ANSWER));
        forceDelete(answerId);
    }

    public void forceDelete(@Nonnull String answerId) throws ServiceException {
        Answer answer = findExists(answerId);

        answerSnapshotService.save(findDetailStateLess(answerId));

        for (AnswerComment answerComment : answer.getComments()) {
            commentCell.forceDelete(ANSWER_COMMENT, answerComment.getUuid());
        }

        updatePictureStatus(answer, null);
        answerRepository.delete(answer.getUuid());
    }

    public Answer findExists(@Nonnull String answerId) throws NotFoundException {
        return answerRepository.findByUuid(answerId).orElseThrow(() -> new NotFoundException(ANSWER));
    }

    public Optional<Answer> find(@Nonnull String answerId) {
        return answerRepository.findByUuid(answerId);
    }

    @Cacheable(value = "cache:cell:answer:detail:stateless", key = "#answerId")
    public AnswerDetail findDetailStateLess(@Nonnull String answerId) throws ServiceException {
        // TODO impl findDetailStateLess
        return findDetail("", answerId, true);
    }

    @Cacheable(value = "cache:cell:snapshot:answer:detail:stateless", key = "#answerId")
    public AnswerDetail findDetailStateLessElseSnapshot(@Nonnull String answerId) throws ServiceException {
        // TODO impl findDetailStateLess
        return findDetailElseSnapshot("", answerId, true);
    }

    public List<AnswerDetail> findDetailByIds(@Nonnull List<String> ids, @Nonnull String userId, boolean fetchQuestionDetail) throws ServiceException {
        List<AnswerDetail> answerDetails = Lists.newArrayList();

        for (String answerId : ids) {
            AnswerDetail answerDetail = findDetail(userId, answerId, false);
            if (fetchQuestionDetail) {
                answerDetail.setQuestion(questionCell.findDetail(userId, answerDetail.getQuestion().getUuid(), false));
            }
            answerDetails.add(answerDetail);
        }

        return answerDetails;
    }

    public List<AnswerDetail> findDetailElseSnapshotByIds(@Nonnull List<String> ids, @Nonnull String userId, boolean fetchQuestionDetail) throws ServiceException {
        List<AnswerDetail> answerDetails = Lists.newArrayList();

        for (String answerId : ids) {
            AnswerDetail answerDetail = findDetailElseSnapshot(userId, answerId, false);
            if (fetchQuestionDetail) {
                answerDetail.setQuestion(questionCell.findDetailElseSnapshot(userId, answerDetail.getQuestion().getUuid(), false));
            }
            answerDetails.add(answerDetail);
        }

        return answerDetails;
    }

    @Cacheable(value = "cache:cell:answer:detail", key = "#userId + ':' + #answerId + ':' + #fetchQuestionDetail")
    public AnswerDetail findDetail(@Nonnull String userId, @Nonnull String answerId, boolean fetchQuestionDetail) throws ServiceException {
        Answer savedAnswer = findExists(answerId);
        AnswerDetail answerDetail = new AnswerDetail().from(savedAnswer);
        answerDetail.setAnswerer(userCell.findDetail(savedAnswer.getAnswerer().getUuid())); // todo user snapshot

        initCount(answerDetail);
        initState(answerDetail, userId);

        answerDetail.setCommentCount(savedAnswer.getComments() == null ? 0 : savedAnswer.getComments().size());
        answerDetail.setComments(commentCell.parseCommentDetail(savedAnswer.getUuid(), userId, savedAnswer.getComments()));

        if (fetchQuestionDetail) {
            answerDetail.setQuestion(questionCell.findDetail(userId, savedAnswer.getQuestion().getUuid(), false));
        }

        return answerDetail;
    }

    @Cacheable(value = "cache:cell:snapshot:answer:detail", key = "#userId + ':' + #answerId + ':' + #fetchQuestionDetail")
    public AnswerDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String answerId, boolean fetchQuestionDetail) throws ServiceException {
        if (checkExists(answerId)) {
            return findDetail(userId, answerId, fetchQuestionDetail);
        }

        AnswerSnapshot answerSnapshot = answerSnapshotService.findByAnswerId(answerId);

        // basic
        AnswerDetail answerDetail = new AnswerDetail();
        answerDetail.setUuid(answerSnapshot.getAnswerId());
        answerDetail.setContent(answerSnapshot.getContent());
        answerDetail.setAnonymous(answerSnapshot.isAnonymous());
        answerDetail.setAnswerer(userCell.findDetail(answerSnapshot.getAnswererId())); // todo user snapshot
        answerDetail.setLanguage(answerSnapshot.getLanguage());
        answerDetail.setCreationTime(answerSnapshot.getCreationTime());
        answerDetail.setFavoriteCount(answerSnapshot.getFavoriteCount());
        answerDetail.setShareCount(answerSnapshot.getShareCount());
        answerDetail.setUpVoteCount(answerSnapshot.getUpVoteCount());
        answerDetail.setDownVoteCount(answerSnapshot.getDownVoteCount());

        // initCount(answerDetail);
        initState(answerDetail, userId);

        if (fetchQuestionDetail) {
            answerDetail.setQuestion(questionCell.findDetailElseSnapshot(userId, answerSnapshot.getQuestionId(), false));
        }

        answerDetail.setComments(commentCell.findDetailElseSnapshotByIds(ANSWER_COMMENT, answerSnapshot.getCommentIds(), userId));
        answerDetail.setCommentCount(answerSnapshot.getCommentIds() == null ? 0 : answerSnapshot.getCommentIds().size());

        answerDetail.setDeleted(true);
        return answerDetail;
    }

    private AnswerDetail initCount(@Nonnull AnswerDetail answerDetail) throws NotFoundException {
        AnswerInteraction answerInteraction = get(answerDetail.getUuid());
        answerDetail.setFavoriteCount(answerInteraction.getFavoriteCount());
        answerDetail.setShareCount(answerInteraction.getShareCount());
        answerDetail.setUpVoteCount(answerInteraction.getUpVoteCount());
        answerDetail.setDownVoteCount(answerInteraction.getDownVoteCount());
        return answerDetail;
    }

    private AnswerDetail initState(@Nonnull AnswerDetail answerDetail, @Nonnull String userId) throws NotFoundException {
        Optional<AnswerVote> foundAnswerVote = answerVoteRepository.findByUserAndTarget(userId, answerDetail.getUuid());
        foundAnswerVote.ifPresent(answerVote -> {
            answerDetail.setUpVoted(answerVote.getDirection().equals(VoteDirection.UP));
            answerDetail.setDownVoted(answerVote.getDirection().equals(VoteDirection.DOWN));
        });

        answerDetail.setFavorited(isUserFavoriteTheAnswer(userId, answerDetail.getUuid()));
        answerDetail.setMine(answerDetail.getAnswerer().getUuid().equals(userId));
        answerDetail.setDeletable(isDeletable(userId, answerDetail.getUuid()));
        return answerDetail;
    }

    public AnswerInteraction get(String answerId) {
        AnswerInteraction answerInteraction = new AnswerInteraction();
        Answer answer = new Answer();
        answer.setUuid(answerId);
        answerInteraction.setAnswer(answer);
        fill(answerInteraction);
        return answerInteraction;
    }

    public void fill(@Nonnull AnswerInteraction answerInteraction) {
        String answerId = answerInteraction.getAnswer().getUuid();
        checkArgument(!isNullOrEmpty(answerId), "answerId can not be null !");

        answerInteraction.setUpVoteCount(answerVoteRepository.countByTargetAndDirection(answerId, VoteDirection.UP));
        answerInteraction.setDownVoteCount(answerVoteRepository.countByTargetAndDirection(answerId, VoteDirection.DOWN));
        answerInteraction.setShareCount(answerShareRepository.countByTarget(answerId));
        answerInteraction.setFavoriteCount(answerFavoriteRepository.countByTarget(answerId));
        answerInteraction.setScore(calculateScore(answerInteraction.getUpVoteCount(), answerInteraction.getDownVoteCount(), answerInteraction.getShareCount(), answerInteraction.getFavoriteCount()));
    }

    public boolean isDeletable(String userId, String answerId) throws NotFoundException {
        if (!checkExists(answerId)) {
            return false;
        }

        Answer answer = findExists(answerId);

        if (!answer.getAnswerer().getUuid().equals(userId)) {
            return false;
        }

        Question question = answer.getQuestion();

        if (question.isExperience() && question.getAuthor().getUuid().equals(answer.getAnswerer().getUuid())) {
            return false;
        }

        return true;
    }

    public Answer updatePictureStatus(Answer answer, Content content) throws NotFoundException {

        Set<String> usedPictures = retrievePictureIds(content);
        Set<String> notUsedPictures = retrievePictureIds(answer.getContent()).stream().filter(id -> !usedPictures.contains(id)).collect(Collectors.toSet());

        if (usedPictures != null && usedPictures.size() > 0) {
            // TODO check whether or not pic id is valid
            storageLinkRepository.updateState(usedPictures, StorageLink.Status.IN_USE);
        }

        if (notUsedPictures != null && notUsedPictures.size() > 0) {
            storageLinkRepository.updateState(notUsedPictures, StorageLink.Status.PERSISTENT);
        }

        answer.setContent(content);
        return answer;
    }

    public Set<String> retrievePictureIds(Content content) {
        if (content != null && content.getType().equals(Type.PIC)) {
            return ((PicContent) content).getContent().keySet();
        }

        return new HashSet<>();
    }

    public double calculateScore(AnswerDetail answerDetail) {
        return calculateScore(answerDetail.getUpVoteCount(), answerDetail.getDownVoteCount(), answerDetail.getShareCount(), answerDetail.getFavoriteCount());
    }

    public double calculateScore(long upVoteCount, long downVoteCount, long shareCount, long favoriteCount) {
        long upVoteEntropy = upVoteCount * answerWeightConfiguration.getVoteUp();
        long downVoteEntropy = downVoteCount * answerWeightConfiguration.getVoteDown();
        long shareEntropy = shareCount * answerWeightConfiguration.getShare();
        long favoriteEntropy = favoriteCount * answerWeightConfiguration.getFavorite();
        long weight = upVoteEntropy + shareEntropy + favoriteEntropy - downVoteEntropy;

        if (weight == 0) return 0;
        return weight > 0 ? log(weight) : -log(weight);
    }

    private boolean isUserFavoriteTheAnswer(@Nonnull String userId, @Nonnull String answerId) {
        return answerFavoriteRepository.countByUserAndTarget(userId, answerId) != 0;
    }

    public boolean checkExists(String answerId) {
        return answerRepository.exists(answerId);
    }

}
