package com.askdog.service.bo;

import com.askdog.common.Out;
import com.askdog.model.entity.Base;
import com.askdog.model.entity.Comment;
import com.askdog.model.entity.User;

import java.io.Serializable;
import java.util.Date;

public class CommentDetail implements Out<CommentDetail, Comment>, Serializable {

    private static final long serialVersionUID = 3244484083374089451L;

    private String uuid;

    private String content;

    private UserDetail commenter;

    private Date creationTime;

    private String ownerId;

    private boolean isMine;

    private boolean deleted;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDetail getCommenter() {
        return commenter;
    }

    public void setCommenter(UserDetail commenter) {
        this.commenter = commenter;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override public CommentDetail from(Comment comment) {
        this.uuid = comment.getUuid();
        this.content = comment.getContent();
        this.creationTime = comment.getCreationTime();
        return this;
    }
}
