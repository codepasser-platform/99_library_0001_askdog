package com.askdog.web.api.vo.profile;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.State;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;

public class ProfileQuestion implements Out<ProfileQuestion, com.askdog.service.bo.QuestionDetail> {

    private String id;
    private String subject;
    private State state;
    private Locale language;
    private Date creationTime;
    private long viewCount;
    private long upVoteCount;
    private long answerCount;
    private long commentCount;
    private long followCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    @Override
    public ProfileQuestion from(com.askdog.service.bo.QuestionDetail question) {
        Assert.notNull(question);
        setId(question.getUuid());
        setSubject(question.getSubject());
        setState(question.getState());
        setLanguage(question.getLanguage());
        setCreationTime(question.getCreationTime());

        this.viewCount = question.getViewCount();
        this.upVoteCount = question.getUpVoteCount();
        this.setAnswerCount(question.getAnswerCount());
        this.setCommentCount(question.getCommentCount());
        this.setFollowCount(question.getFollowCount());

        return this;
    }
}
