package com.askdog.web.api.vo.profile;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;

public class ProfileAnswer implements Out<ProfileAnswer, com.askdog.service.bo.AnswerDetail> {

    private String id;
    private ProfileAnswerQuestion question;
    private Content content;
    private Locale language;
    private long upVoteCount;
    private long commentCount;
    private long favoriteCount;
    private Date creationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProfileAnswerQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ProfileAnswerQuestion question) {
        this.question = question;
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

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
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

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public ProfileAnswer from(com.askdog.service.bo.AnswerDetail answer) {
        this.id = answer.getUuid();
        this.question = new ProfileAnswerQuestion().from(answer.getQuestion());
        this.content = answer.getContent();
        this.language = answer.getLanguage();
        this.upVoteCount = answer.getUpVoteCount();
        this.commentCount = answer.getCommentCount();
        this.creationTime = answer.getCreationTime();
        this.favoriteCount = answer.getFavoriteCount();
        return this;
    }

    public class ProfileAnswerQuestion implements Out<ProfileAnswerQuestion, com.askdog.service.bo.QuestionDetail> {

        private String id;
        private String subject;
        private State state;
        private Locale language;
        private Date creationTime;
        private long viewCount;

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

        @Override
        public ProfileAnswerQuestion from(com.askdog.service.bo.QuestionDetail question) {
            Assert.notNull(question);
            setId(question.getUuid());
            setSubject(question.getSubject());
            setState(question.getState());
            setLanguage(question.getLanguage());
            setCreationTime(question.getCreationTime());
            this.viewCount = question.getViewCount();
            return this;
        }
    }

}
