package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;

public class QuestionFollow implements Out<QuestionFollow, com.askdog.service.bo.QuestionDetail> {

    private String id;
    private String subject;
    private boolean anonymous;
    private boolean experience;
    private PresentationUser author;
    private State state;
    private Locale language;
    private Date creationTime;
    private long viewCount;
    private long followCount;
    private boolean followed;
    private boolean mine;
    private long answerCount;
    private String location;
    private boolean deleted;
    private FollowAnswerDetail bestAnswer;

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

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }

    public PresentationUser getAuthor() {
        return author;
    }

    public void setAuthor(PresentationUser author) {
        this.author = author;
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

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public FollowAnswerDetail getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(FollowAnswerDetail bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    @Override
    public QuestionFollow from(com.askdog.service.bo.QuestionDetail question) {
        Assert.notNull(question);
        setId(question.getUuid());
        setSubject(question.getSubject());
        setAnonymous(question.isAnonymous());
        setExperience(question.isExperience());
        if (!question.isAnonymous()) {
            setAuthor(new PresentationUser().from(question.getAuthor()));
        }
        setState(question.getState());
        setLanguage(question.getLanguage());
        setLocation(question.getLocation());
        setCreationTime(question.getCreationTime());

        this.viewCount = question.getViewCount();
        this.followCount = question.getFollowCount();
        this.setFollowed(question.isFollowed());
        this.mine = question.isMine();
        this.setAnswerCount(question.getAnswerCount());
        this.deleted = question.isDeleted();
        if (question.getBestAnswer() != null) {
            this.bestAnswer = new FollowAnswerDetail().from(question.getBestAnswer());
        }
        return this;
    }

    public static class FollowAnswerDetail implements Out<FollowAnswerDetail, com.askdog.service.bo.AnswerDetail> {

        private String id;
        private Content content;
        private Locale language;
        private long upVoteCount;
        private boolean upVoted;
        private boolean deleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public boolean isUpVoted() {
            return upVoted;
        }

        public void setUpVoted(boolean upVoted) {
            this.upVoted = upVoted;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        @Override
        public FollowAnswerDetail from(com.askdog.service.bo.AnswerDetail answer) {
            this.id = answer.getUuid();
            this.content = answer.getContent();
            this.language = answer.getLanguage();
            this.upVoteCount = answer.getUpVoteCount();
            this.upVoted = answer.isUpVoted();
            this.deleted = answer.isDeleted();
            return this;
        }

    }
}
