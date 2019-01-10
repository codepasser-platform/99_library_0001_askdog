package com.askdog.service.impl;

import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.snapshot.CommentSnapshot.CommentType;
import com.askdog.model.entity.AnswerComment;
import com.askdog.model.entity.builder.AnswerCommentBuilder;
import com.askdog.model.repository.AnswerCommentRepository;
import com.askdog.service.AnswerCommentService;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.PureComment;
import com.askdog.service.bo.PureReport;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.annotation.EvictAnswerCache;
import com.askdog.service.impl.annotation.EvictQuestionCache;
import com.askdog.service.impl.cell.CommentCell;
import com.askdog.service.impl.cell.ReportCell;
import com.askdog.service.impl.cell.UserCell;
import com.askdog.service.snapshot.CommentSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

import static com.askdog.service.exception.NotFoundException.Error.ANSWER_COMMENT;

@Service
public class AnswerCommentServiceImpl implements AnswerCommentService {

    @Autowired private UserCell userCell;
    @Autowired private ReportCell reportCell;
    @Autowired private CommentCell commentCell;
    @Autowired private AnswerCommentRepository answerCommentRepository;
    @Autowired private CommentSnapshotService commentSnapshotService;

    @Override
    @Nonnull
    public CommentDetail find(@Nonnull String answerCommentId) throws ServiceException {
        AnswerComment answerComment = answerCommentRepository.findByUuid(answerCommentId).orElseThrow(() -> new NotFoundException(ANSWER_COMMENT));
        return commentCell.findDetailStateLess(answerComment.getAnswer().getUuid(), answerComment);
    }

    @Nonnull
    @Override
    public CommentDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String commentId) throws ServiceException {
        return commentCell.findDetailElseSnapshot(CommentType.ANSWER_COMMENT, userId, commentId);
    }

    @Override
    @Nonnull
    @EvictAnswerCache
    @EvictQuestionCache
    public CommentDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull PureComment pureComment) throws ServiceException {
        AnswerComment answerComment = AnswerCommentBuilder.answerCommentBuilder()
                .content(pureComment.getContent())
                .commenter(userId)
                .creationTime(new Date())
                .answer(answerId).build();
        AnswerComment savedComment = answerCommentRepository.save(answerComment);

        return commentCell.findDetailStateLess(answerId, savedComment);
    }

    @Override
    @EvictAnswerCache
    @EvictQuestionCache
    public void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull String answerCommentId) throws ServiceException {
        AnswerComment answerComment = answerCommentRepository.findByCommenter_UuidAndUuid(userId, answerCommentId).orElseThrow(() -> new NotFoundException(ANSWER_COMMENT));

        CommentDetail commentDetail = new CommentDetail().from(answerComment);
        commentDetail.setOwnerId(answerComment.getAnswer().getUuid());
        commentDetail.setMine(answerComment.getCommenter().getUuid().equals(userId));
        commentDetail.setCommenter(userCell.findDetail(answerComment.getCommenter().getUuid()));
        commentSnapshotService.save(commentDetail);

        answerCommentRepository.delete(answerComment.getUuid());
    }

    @Override
    @Nonnull
    @EvictAnswerCache
    @EvictQuestionCache
    public AnswerComment update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String answerId, @Nonnull String answerCommentId, @Nonnull String comment) throws ServiceException {
        AnswerComment answerComment = answerCommentRepository.findByCommenter_UuidAndUuid(userId, answerCommentId).orElseThrow(() -> new NotFoundException(ANSWER_COMMENT));
        answerComment.setContent(comment);
        return answerCommentRepository.save(answerComment);
    }

    @Nonnull
    @Override
    public List<CommentDetail> findByAnswerId(@Nonnull String answerId) throws ServiceException {
        return commentCell.parseCommentDetailStateLess(answerId, answerCommentRepository.findByAnswerUuidOrderByCreationTimeDesc(answerId));
    }

    @Override
    public void report(String userId, @Nonnull String commentId, @Nonnull PureReport pureReport) throws ServiceException {
        userCell.checkUserId(userId, true);
        checkCommentId(commentId);
        reportCell.report(userId, TargetType.ANSWER_COMMENT, commentId, pureReport);
    }

    private void checkCommentId(String commentId) throws NotFoundException {
        if (!answerCommentRepository.exists(commentId)) {
            throw new NotFoundException(ANSWER_COMMENT);
        }
    }
}
