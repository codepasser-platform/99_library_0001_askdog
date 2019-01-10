package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;

public class Experience implements Out<Experience, com.askdog.service.bo.QuestionDetail> {

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
    private boolean mine;
    private long answerCount;
    private long commentCount;
    private ExperienceAnswerDetail experienceAnswer;
    private boolean deletable;

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

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public ExperienceAnswerDetail getExperienceAnswer() {
        return experienceAnswer;
    }

    public void setExperienceAnswer(ExperienceAnswerDetail experienceAnswer) {
        this.experienceAnswer = experienceAnswer;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    @Override
    public Experience from(com.askdog.service.bo.QuestionDetail question) {
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
        setCreationTime(question.getCreationTime());

        this.viewCount = question.getViewCount();
        this.followCount = question.getFollowCount();
        this.mine = question.isMine();
        this.setAnswerCount(question.getAnswerCount());
        this.setCommentCount(question.getCommentCount());
        this.deletable = question.isDeletable();

        return this;
    }

    public static class ExperienceAnswerDetail implements Out<ExperienceAnswerDetail, com.askdog.service.bo.AnswerDetail> {

        private String id;
        private boolean isAnonymous;
        private PresentationUser answerer;
        private Content content;
        private Locale language;
        private boolean mine;
        private Date creationTime;
        private long upVoteCount;
        private boolean upVoted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isAnonymous() {
            return isAnonymous;
        }

        public void setAnonymous(boolean anonymous) {
            isAnonymous = anonymous;
        }

        public PresentationUser getAnswerer() {
            return answerer;
        }

        public void setAnswerer(PresentationUser answerer) {
            this.answerer = answerer;
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

        public boolean isMine() {
            return mine;
        }

        public void setMine(boolean mine) {
            this.mine = mine;
        }

        public Date getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
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

        @Override
        public ExperienceAnswerDetail from(com.askdog.service.bo.AnswerDetail answer) {
            this.id = answer.getUuid();
            this.isAnonymous = answer.isAnonymous();
            if (!answer.isAnonymous()) {
                this.answerer = new PresentationUser().from(answer.getAnswerer());
            }
            this.content = answer.getContent();
            this.language = answer.getLanguage();
            this.creationTime = answer.getCreationTime();
            this.mine = answer.isMine();
            this.upVoteCount = answer.getUpVoteCount();
            this.upVoted = answer.isUpVoted();
            return this;
        }

    }
}
