package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.Label;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import com.askdog.service.bo.*;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class PresentationQuestion implements Out<PresentationQuestion, com.askdog.service.bo.QuestionDetail> {

    private String id;
    private String subject;
    private Content content;
    private PresentationUser author;
    private State state;
    private Locale language;
    private Date creationTime;
    private Set<String> labels;
    private int commentCount;

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

    @Override
    public PresentationQuestion from(com.askdog.service.bo.QuestionDetail question) {
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
        return this;
    }
}
