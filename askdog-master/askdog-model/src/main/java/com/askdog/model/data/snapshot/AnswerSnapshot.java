package com.askdog.model.data.snapshot;

import com.askdog.model.entity.inner.Content;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Document(collection = "ad_snapshot_answer")
public class AnswerSnapshot extends Snapshot<AnswerSnapshot> {

    private String answerId;
    private String questionId;
    private Content content;
    private boolean anonymous;
    private String answererId;
    private Locale language;
    private long upVoteCount;
    private long downVoteCount;
    private long shareCount;
    private long favoriteCount;
    private Date creationTime;
    private List<String> commentIds;

    public String getAnswerId() {
        return answerId;
    }

    public AnswerSnapshot setAnswerId(String answerId) {
        this.answerId = answerId;
        return this;
    }

    public String getQuestionId() {
        return questionId;
    }

    public AnswerSnapshot setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public AnswerSnapshot setContent(Content content) {
        this.content = content;
        return this;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public AnswerSnapshot setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public String getAnswererId() {
        return answererId;
    }

    public AnswerSnapshot setAnswererId(String answererId) {
        this.answererId = answererId;
        return this;
    }

    public Locale getLanguage() {
        return language;
    }

    public AnswerSnapshot setLanguage(Locale language) {
        this.language = language;
        return this;
    }

    public long getUpVoteCount() {
        return upVoteCount;
    }

    public AnswerSnapshot setUpVoteCount(long upVoteCount) {
        this.upVoteCount = upVoteCount;
        return this;
    }

    public long getDownVoteCount() {
        return downVoteCount;
    }

    public AnswerSnapshot setDownVoteCount(long downVoteCount) {
        this.downVoteCount = downVoteCount;
        return this;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public AnswerSnapshot setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public AnswerSnapshot setCommentIds(List<String> commentIds) {
        this.commentIds = commentIds;
        return this;
    }

}
