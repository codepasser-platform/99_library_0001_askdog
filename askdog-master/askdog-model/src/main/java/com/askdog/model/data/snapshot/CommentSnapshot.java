package com.askdog.model.data.snapshot;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "ad_snapshot_comment")
public class CommentSnapshot extends Snapshot<CommentSnapshot> {

    public enum CommentType {
        QUESTION_COMMENT, ANSWER_COMMENT
    }

    private CommentType commentType;
    private String commentId;
    private String content;
    private String commenterId;
    private Date creationTime;
    private String ownerId;

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public String getCommentId() {
        return commentId;
    }

    public CommentSnapshot setCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public CommentSnapshot setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public CommentSnapshot setCommenterId(String commenterId) {
        this.commenterId = commenterId;
        return this;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public CommentSnapshot setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public CommentSnapshot setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
