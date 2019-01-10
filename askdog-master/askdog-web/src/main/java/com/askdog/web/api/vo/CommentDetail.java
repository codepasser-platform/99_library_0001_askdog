package com.askdog.web.api.vo;

import com.askdog.common.Out;
import org.springframework.util.Assert;

import java.util.Date;

public class CommentDetail implements Out<CommentDetail, com.askdog.service.bo.CommentDetail> {

    private String id;
    private String owner;
    private String content;
    private PresentationUser commenter;
    private Date creationTime;
    private boolean isMine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PresentationUser getCommenter() {
        return commenter;
    }

    public void setCommenter(PresentationUser commenter) {
        this.commenter = commenter;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    @Override
    public CommentDetail from(com.askdog.service.bo.CommentDetail commentDetail) {
        Assert.notNull(commentDetail);
        setId(commentDetail.getUuid());
        setOwner(commentDetail.getOwnerId());
        setContent(commentDetail.getContent());
        setCommenter(new PresentationUser().from(commentDetail.getCommenter()));
        setCreationTime(commentDetail.getCreationTime());
        setMine(commentDetail.isMine());
        return this;
    }

}
