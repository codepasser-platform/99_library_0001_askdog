package com.askdog.service.bo;

import com.askdog.common.Out;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.inner.Content;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnswerDetail implements Out<AnswerDetail, Answer>, Serializable {

    private static final long serialVersionUID = -1332420647558621551L;

    private String uuid;

    private QuestionDetail question;

    private Content content;

    private boolean anonymous;

    private UserDetail answerer;

    private Locale language;

    private long upVoteCount;

    private long downVoteCount;

    private long shareCount;

    private long favoriteCount;

    private long commentCount;

    private boolean isUpVoted;

    private boolean isDownVoted;

    private boolean isFavorited;

    private boolean isMine;

    private Date creationTime;

    private List<CommentDetail> comments = Lists.newArrayList();

    private boolean deletable;

    private boolean deleted;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public QuestionDetail getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDetail question) {
        this.question = question;
    }

    public UserDetail getAnswerer() {
        return answerer;
    }

    public void setAnswerer(UserDetail answerer) {
        this.answerer = answerer;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public long getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public long getDownVoteCount() {
        return downVoteCount;
    }

    public void setDownVoteCount(long downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isUpVoted() {
        return isUpVoted;
    }

    public void setUpVoted(boolean upVoted) {
        isUpVoted = upVoted;
    }

    public boolean isDownVoted() {
        return isDownVoted;
    }

    public void setDownVoted(boolean downVoted) {
        isDownVoted = downVoted;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public List<CommentDetail> getComments() {
        return comments;
    }

    public void setComments(List<CommentDetail> comments) {
        this.comments = comments;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override public AnswerDetail from(Answer entity) {
        this.uuid = entity.getUuid();
        this.content = entity.getContent();
        this.anonymous = entity.isAnonymous();
        this.language = entity.getLanguage();
        this.creationTime = entity.getCreationTime();
        return this;
    }
}
