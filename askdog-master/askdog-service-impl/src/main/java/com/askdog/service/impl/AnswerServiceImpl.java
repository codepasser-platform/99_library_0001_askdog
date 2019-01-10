package com.askdog.service.impl;

import com.askdog.model.data.Actions.AnswerFavorite;
import com.askdog.model.data.Actions.AnswerVote;
import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.inner.VoteDirection;
import com.askdog.model.entity.Answer;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.model.repository.mongo.action.AnswerFavoriteRepository;
import com.askdog.model.repository.mongo.action.AnswerVoteRepository;
import com.askdog.service.AnswerService;
import com.askdog.service.bo.*;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.annotation.EvictAnswerCache;
import com.askdog.service.impl.annotation.EvictQuestionCache;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.service.impl.cell.ReportCell;
import com.askdog.service.impl.cell.UserCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Optional;

import static com.askdog.model.data.builder.AnswerFavoriteBuilder.questionFavoriteBuilder;
import static com.askdog.model.data.builder.VoteBuilders.answerVoteBuilder;
import static com.askdog.model.data.inner.VoteDirection.DOWN;
import static com.askdog.model.data.inner.VoteDirection.UP;
import static com.askdog.service.exception.NotFoundException.Error.ANSWER;

@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class AnswerServiceImpl implements AnswerService {

    @Autowired private AnswerRepository answerRepository;
    @Autowired private AnswerFavoriteRepository answerFavoriteRepository;
    @Autowired private AnswerVoteRepository answerVoteRepository;
    @Autowired private ReportCell reportCell;
    @Autowired private UserCell userCell;
    @Autowired private AnswerCell answerCell;
    @Autowired private QuestionCell questionCell;

    @Override
    @Nonnull
    public Answer find(@Nonnull String answerId) throws ServiceException {
        return answerCell.findExists(answerId);
    }

    @Override
    @Nonnull
    public AnswerDetail findDetail(String userId, @Nonnull String answerId) throws ServiceException {
        return answerCell.findDetail(userId, answerId, true);
    }

    @Nonnull
    @Override
    public AnswerDetail findDetailElseSnapshot(String userId, @Nonnull String answerId) throws ServiceException {
        return answerCell.findDetailElseSnapshot(userId, answerId, true);
    }

    @Override
    @Nonnull
    @EvictQuestionCache
    public AnswerDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull PureAnswer pureAnswer) throws ServiceException {
        return answerCell.create(userId, questionId, pureAnswer);
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {
        answerCell.delete(userId, answerId);
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public void update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull AmendedAnswer amendedAnswer) throws ServiceException {
        Answer answer = answerRepository.findByAnswerer_UuidAndUuid(userId, answerId)
                .orElseThrow(() -> new NotFoundException(ANSWER));
        answerCell.updatePictureStatus(answer, amendedAnswer.getContent());
        answer.setAnonymous(amendedAnswer.isAnonymous());

        answerCell.save(answer);

        if (amendedAnswer.isFollow()) {
            questionCell.follow(userId, answer.getQuestion().getUuid());

        } else {
            questionCell.unfollow(userId, answer.getQuestion().getUuid());
        }
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public void favorite(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {
        if (answerFavoriteRepository.countByUserAndTarget(userId, answerId) == 0) {
            Answer answer = answerRepository.findByUuid(answerId).orElseThrow(() -> new NotFoundException(ANSWER));
            userCell.checkUserId(userId);
            AnswerFavorite answerFavorite = questionFavoriteBuilder()
                    .user(userId)
                    .question(answer.getQuestion().getUuid())
                    .answer(answerId)
                    .build();
            answerFavoriteRepository.save(answerFavorite);
        }
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public void unfavorite(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {
        answerFavoriteRepository.deleteByUserAndTarget(userId, answerId);
    }

    @Override
    public void report(String userId, @Nonnull String answerId, @Nonnull PureReport pureReport) throws ServiceException {
        userCell.checkUserId(userId, true);
        checkAnswerId(answerId);
        reportCell.report(userId, TargetType.ANSWER, answerId, pureReport);
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public VoteCount vote(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull VoteDirection direction) throws ServiceException {
        userCell.checkUserId(userId);
        checkAnswerId(answerId);
        Optional<AnswerVote> foundAnswerVote = answerVoteRepository.findByUserAndTarget(userId, answerId);
        if (!foundAnswerVote.isPresent()) {
            AnswerVote answerVote = answerVoteBuilder()
                    .user(userId)
                    .question(answerCell.findExists(answerId).getQuestion().getUuid())
                    .target(answerId)
                    .targetType(TargetType.ANSWER)
                    .direction(direction)
                    .build();
            answerVoteRepository.save(answerVote);
        } else {
            if (foundAnswerVote.get().getDirection() != direction) {
                AnswerVote answerVote = foundAnswerVote.get();
                answerVote.setDirection(direction);
                answerVote.setTime(new Date());
                answerVoteRepository.save(answerVote);
            }
        }

        VoteCount voteCount = new VoteCount();
        voteCount.setUpVoteCount(answerVoteRepository.countByTargetAndDirection(answerId, UP));
        voteCount.setDownVoteCount(answerVoteRepository.countByTargetAndDirection(answerId, DOWN));
        return voteCount;
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public VoteCount unvote(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId) throws ServiceException {
        userCell.checkUserId(userId);
        checkAnswerId(answerId);
        answerVoteRepository.deleteByUserAndTarget(userId, answerId);

        VoteCount voteCount = new VoteCount();
        voteCount.setUpVoteCount(answerVoteRepository.countByTargetAndDirection(answerId, UP));
        voteCount.setDownVoteCount(answerVoteRepository.countByTargetAndDirection(answerId, DOWN));
        return voteCount;
    }

    private void checkAnswerId(String answerId) throws NotFoundException {
        if (!answerRepository.exists(answerId)) {
            throw new NotFoundException(ANSWER);
        }
    }

}