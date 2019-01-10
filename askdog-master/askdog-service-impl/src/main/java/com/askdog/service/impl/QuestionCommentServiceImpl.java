package com.askdog.service.impl;

import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.snapshot.CommentSnapshot.CommentType;
import com.askdog.model.entity.QuestionComment;
import com.askdog.model.entity.builder.QuestionCommentBuilder;
import com.askdog.model.repository.QuestionCommentRepository;
import com.askdog.service.QuestionCommentService;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.PureComment;
import com.askdog.service.bo.PureReport;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
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

import static com.askdog.service.exception.NotFoundException.Error.QUESTION_COMMENT;

@Service
public class QuestionCommentServiceImpl implements QuestionCommentService {

    @Autowired private QuestionCommentRepository questionCommentRepository;
    @Autowired private ReportCell reportCell;
    @Autowired private UserCell userCell;
    @Autowired private CommentCell commentCell;
    @Autowired private CommentSnapshotService commentSnapshotService;

    @Override
    @Nonnull
    public CommentDetail find(@Nonnull String questionCommentId) throws ServiceException {
        QuestionComment questionComment = questionCommentRepository.findByUuid(questionCommentId).orElseThrow(() -> new NotFoundException(QUESTION_COMMENT));
        return commentCell.findDetailStateLess(questionComment.getQuestion().getUuid(), questionComment);
    }

    @Nonnull
    @Override
    public CommentDetail findDetailElseSnapshot(@Nonnull String userId, @Nonnull String commentId) throws ServiceException {
        return commentCell.findDetailElseSnapshot(CommentType.QUESTION_COMMENT, userId, commentId);
    }

    @Override
    @Nonnull
    @EvictQuestionCache
    public CommentDetail create(@Nonnull String userId, @Nonnull String questionId, @Nonnull PureComment pureComment) throws ServiceException {
        QuestionComment questionComment = QuestionCommentBuilder.questionCommentBuilder()
                .content(pureComment.getContent())
                .commenter(userId)
                .creationTime(new Date())
                .question(questionId).build();
        QuestionComment savedComment = questionCommentRepository.save(questionComment);
        return commentCell.findDetailStateLess(savedComment.getQuestion().getUuid(), savedComment);
    }

    @Override
    @EvictQuestionCache
    public void delete(@Nonnull String userId, @Nonnull String questionId, @Nonnull String questionCommentId) throws ServiceException {
        QuestionComment questionComment = questionCommentRepository.findByCommenter_UuidAndUuid(userId, questionCommentId).orElseThrow(() -> new NotFoundException(QUESTION_COMMENT));

        CommentDetail commentDetail = new CommentDetail().from(questionComment);
        commentDetail.setOwnerId(questionComment.getQuestion().getUuid());
        commentDetail.setMine(questionComment.getCommenter().getUuid().equals(userId));
        commentDetail.setCommenter(userCell.findDetail(questionComment.getCommenter().getUuid()));
        commentSnapshotService.save(commentDetail);

        questionCommentRepository.delete(questionComment.getUuid());
    }

    @Override
    @Nonnull
    @EvictQuestionCache
    public QuestionComment update(@Nonnull String userId, @Nonnull String questionId, @Nonnull String questionCommentId, @Nonnull String comment) throws ServiceException {
        QuestionComment questionComment = questionCommentRepository.findByCommenter_UuidAndUuid(userId, questionCommentId).orElseThrow(() -> new NotFoundException(QUESTION_COMMENT));
        questionComment.setContent(comment);
        return questionCommentRepository.save(questionComment);
    }

    @Nonnull
    @Override
    public List<CommentDetail> findByQuestionId(@Nonnull String questionId) throws ServiceException {
        return commentCell.parseCommentDetailStateLess(questionId, questionCommentRepository.findByQuestionUuidOrderByCreationTimeDesc(questionId));
    }

    @Override
    public void report(String userId, @Nonnull String commentId, @Nonnull PureReport pureReport) throws ServiceException {
        userCell.checkUserId(userId, true);
        checkQuestionCommentId(commentId);
        reportCell.report(userId, TargetType.QUESTION_COMMENT, commentId, pureReport);
    }

    private void checkQuestionCommentId(String commentId) throws NotFoundException {
        if (!questionCommentRepository.exists(commentId)) {
            throw new NotFoundException(QUESTION_COMMENT);
        }
    }
}
