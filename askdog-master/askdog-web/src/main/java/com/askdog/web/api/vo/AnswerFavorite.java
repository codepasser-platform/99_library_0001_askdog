package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import com.askdog.service.bo.AnswerDetail;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class AnswerFavorite implements Out<AnswerFavorite, AnswerDetail> {
    private String id;
    private boolean isAnonymous;
    private PresentationUser answerer;
    private Content content;
    private Locale language;
    private long upVoteCount;
    private boolean upVoted;
    private boolean mine;
    private String questionId;
    private Date creationTime;
    private boolean deleted;
    private AnswerFavoriteQuestion question;

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

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public AnswerFavoriteQuestion getQuestion() {
        return question;
    }

    public void setQuestion(AnswerFavoriteQuestion question) {
        this.question = question;
    }

    @Override public AnswerFavorite from(AnswerDetail answer) {
        this.id = answer.getUuid();
        this.isAnonymous = answer.isAnonymous();
        if (!answer.isAnonymous()) {
            this.answerer = new PresentationUser().from(answer.getAnswerer());
        }
        this.content = answer.getContent();
        this.language = answer.getLanguage();
        this.creationTime = answer.getCreationTime();
        this.upVoteCount = answer.getUpVoteCount();
        this.upVoted = answer.isUpVoted();
        this.mine = answer.isMine();
        this.questionId = answer.getQuestion().getUuid();
        this.deleted = answer.isDeleted();
        this.question = new AnswerFavoriteQuestion().from(answer.getQuestion());
        return this;
    }

    public class AnswerFavoriteQuestion implements Out<AnswerFavoriteQuestion, com.askdog.service.bo.QuestionDetail> {

        private String id;
        private String subject;
        private Content content;
        private PresentationUser author;
        private State state;
        private Locale language;
        private Date creationTime;
        private Set<String> labels;
        private int commentCount;
        private boolean experience;
        private boolean deleted;

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

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
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

        public Set<String> getLabels() {
            return labels;
        }

        public void setLabels(Set<String> labels) {
            this.labels = labels;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public boolean isExperience() {
            return experience;
        }

        public void setExperience(boolean experience) {
            this.experience = experience;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        @Override
        public AnswerFavoriteQuestion from(com.askdog.service.bo.QuestionDetail question) {
            Assert.notNull(question);
            setId(question.getUuid());
            setSubject(question.getSubject());
            setContent(question.getContent());
            setAuthor(new PresentationUser().from(question.getAuthor()));
            setState(question.getState());
            setLanguage(question.getLanguage());
            setCreationTime(question.getCreationTime());
            setLabels(question.getLabels());
            setCommentCount(question.getComments() == null ? 0 : question.getComments().size());
            setExperience(question.isExperience());
            setDeleted(question.isDeleted());
            return this;
        }
    }
}
