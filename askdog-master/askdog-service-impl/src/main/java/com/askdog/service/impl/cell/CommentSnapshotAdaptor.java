package com.askdog.service.impl.cell;

import com.askdog.model.data.snapshot.CommentSnapshot;
import com.askdog.model.entity.AnswerComment;
import com.askdog.model.entity.QuestionComment;

import java.util.Date;

import static com.askdog.model.data.snapshot.CommentSnapshot.CommentType.ANSWER_COMMENT;
import static com.askdog.model.data.snapshot.CommentSnapshot.CommentType.QUESTION_COMMENT;

class CommentSnapshotAdaptor extends CommentSnapshot {

    private CommentSnapshot commentSnapshot;
    private QuestionComment questionComment;
    private AnswerComment answerComment;

    CommentSnapshotAdaptor(CommentSnapshot commentSnapshot) {
        reset();
        this.commentSnapshot = commentSnapshot;
    }

    CommentSnapshotAdaptor(QuestionComment questionComment) {
        reset();
        this.questionComment = questionComment;
    }

    CommentSnapshotAdaptor(AnswerComment answerComment) {
        reset();
        this.answerComment = answerComment;
    }

    public String getCommentId() {
        return commentSnapshot != null ? commentSnapshot.getCommentId()
                : questionComment != null ? questionComment.getUuid()
                : answerComment != null ? answerComment.getUuid() : null;
    }

    public String getContent() {
        return commentSnapshot != null ? commentSnapshot.getContent()
                : questionComment != null ? questionComment.getContent()
                : answerComment != null ? answerComment.getContent() : null;
    }


    public String getCommenterId() {
        return commentSnapshot != null ? commentSnapshot.getCommenterId()
                : questionComment != null ? questionComment.getCommenter().getUuid()
                : answerComment != null ? answerComment.getCommenter().getUuid() : null;
    }

    public Date getCreationTime() {
        return commentSnapshot != null ? commentSnapshot.getCreationTime()
                : questionComment != null ? questionComment.getCreationTime()
                : answerComment != null ? answerComment.getCreationTime() : null;
    }

    public String getOwnerId() {
        return commentSnapshot != null ? commentSnapshot.getOwnerId()
                : questionComment != null ? questionComment.getQuestion().getUuid()
                : answerComment != null ? answerComment.getAnswer().getUuid() : null;
    }

    public CommentSnapshot buildCommentSnapshot() {
        CommentSnapshot commentSnapshot = new CommentSnapshot();
        commentSnapshot.setCommentId(getCommentId());
        commentSnapshot.setContent(getContent());
        commentSnapshot.setCommenterId(getCommenterId());
        commentSnapshot.setCreationTime(getCreationTime());
        commentSnapshot.setOwnerId(getOwnerId());
        commentSnapshot.setSnapshotTime(new Date());
        commentSnapshot.setCommentType(questionComment != null ? QUESTION_COMMENT : ANSWER_COMMENT);
        return commentSnapshot;
    }

    public boolean isDeleted() {
        return commentSnapshot != null;
    }

    private void reset() {
        this.commentSnapshot = null;
        this.questionComment = null;
        this.answerComment = null;
    }
}
