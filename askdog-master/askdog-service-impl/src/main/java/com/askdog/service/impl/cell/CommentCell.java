package com.askdog.service.impl.cell;

import com.askdog.model.data.snapshot.CommentSnapshot.CommentType;
import com.askdog.model.entity.Comment;
import com.askdog.model.repository.AnswerCommentRepository;
import com.askdog.model.repository.QuestionCommentRepository;
import com.askdog.model.repository.mongo.snapshot.CommentSnapshotRepository;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.askdog.service.exception.NotFoundException.Error.*;

@Component
@Transactional
public class CommentCell {

    @Autowired private UserCell userCell;
    @Autowired private QuestionCommentRepository questionCommentRepository;
    @Autowired private AnswerCommentRepository answerCommentRepository;
    @Autowired private CommentSnapshotRepository commentSnapshotRepository;

    public CommentDetail findDetailStateLess(String ownerId, Comment comment) throws ServiceException {
        CommentDetail commentDetail = new CommentDetail().from(comment);
        commentDetail.setOwnerId(ownerId);
        commentDetail.setCommenter(userCell.findDetail(comment.getCommenter().getUuid()));
        return commentDetail;
    }

    public List<CommentDetail> parseCommentDetailStateLess(String ownerId, List<? extends Comment> comments) throws ServiceException {
        List<CommentDetail> commentDetails = Lists.newArrayList();

        if (comments != null) {
            for (Comment comment : comments) {
                CommentDetail commentDetail = new CommentDetail().from(comment);
                commentDetail.setOwnerId(ownerId);
                commentDetail.setCommenter(userCell.findDetail(comment.getCommenter().getUuid()));
                commentDetails.add(commentDetail);
            }
        }

        return commentDetails;
    }

    public List<CommentDetail> parseCommentDetail(String ownerId, String userId, List<? extends Comment> comments) throws ServiceException {
        List<CommentDetail> commentDetails = Lists.newArrayList();

        if (comments != null) {
            for (Comment comment : comments) {
                CommentDetail commentDetail = new CommentDetail().from(comment);
                commentDetail.setOwnerId(ownerId);
                commentDetail.setMine(comment.getCommenter().getUuid().equals(userId));
                commentDetail.setCommenter(userCell.findDetail(comment.getCommenter().getUuid()));
                commentDetails.add(commentDetail);
            }
        }

        return commentDetails;
    }

    public void forceDelete(@Nonnull CommentType commentType, @Nonnull String commentId) throws ServiceException {
        commentSnapshotRepository.save(findExists(commentType, commentId).buildCommentSnapshot());

        if (commentType.equals(CommentType.QUESTION_COMMENT)) {
            questionCommentRepository.delete(commentId);

        } else {
            answerCommentRepository.delete(commentId);
        }
    }

    public List<CommentDetail> findDetailElseSnapshotByIds(@Nonnull CommentType commentType, @Nonnull List<String> ids, @Nonnull String userId) throws ServiceException {
        List<CommentDetail> commentDetails = Lists.newArrayList();

        for (String answerId : ids) {
            commentDetails.add(findDetailElseSnapshot(commentType, userId, answerId));
        }

        return commentDetails;
    }

    public CommentDetail findDetailStateLessElseSnapshot(@NotNull CommentType commentType, @NotNull String commentId) throws ServiceException {
        // TODO impl findDetailStateLess
        return findDetailElseSnapshot(commentType, "", commentId);
    }

    public CommentDetail findDetailElseSnapshot(CommentType commentType, String userId, String commentId) throws ServiceException {

        CommentSnapshotAdaptor commentSnapshotAdaptor =
                ((commentType.equals(CommentType.QUESTION_COMMENT) && !checkQuestionCommentExists(commentId))
                        || (commentType.equals(CommentType.ANSWER_COMMENT) && !checkAnswerCommentExists(commentId)))
                        ? new CommentSnapshotAdaptor(commentSnapshotRepository.findByCommentId(commentId).orElseThrow(() -> new NotFoundException(COMMENT_SNAPSHOT)))
                        : findExists(commentType, commentId);

        CommentDetail commentDetail = new CommentDetail();
        commentDetail.setUuid(commentSnapshotAdaptor.getCommentId());
        commentDetail.setContent(commentSnapshotAdaptor.getContent());
        commentDetail.setCommenter(userCell.findDetail(commentSnapshotAdaptor.getCommenterId()));
        commentDetail.setCreationTime(commentSnapshotAdaptor.getCreationTime());
        commentDetail.setMine(commentSnapshotAdaptor.getCommenterId().equals(userId));
        commentDetail.setOwnerId(commentSnapshotAdaptor.getOwnerId());
        commentDetail.setDeleted(commentSnapshotAdaptor.isDeleted());
        return commentDetail;
    }

    public CommentSnapshotAdaptor findExists(CommentType commentType, @Nonnull String commentId) throws NotFoundException {
        return commentType.equals(CommentType.QUESTION_COMMENT)
                ? new CommentSnapshotAdaptor(questionCommentRepository.findByUuid(commentId).orElseThrow(() -> new NotFoundException(QUESTION_COMMENT)))
                : new CommentSnapshotAdaptor(answerCommentRepository.findByUuid(commentId).orElseThrow(() -> new NotFoundException(ANSWER_COMMENT)));
    }

    private boolean checkQuestionCommentExists(String commentId) {
        return questionCommentRepository.exists(commentId);
    }

    private boolean checkAnswerCommentExists(String commentId) {
        return answerCommentRepository.exists(commentId);
    }
}
