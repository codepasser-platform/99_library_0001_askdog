package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.inner.Content;
import com.askdog.service.bo.*;

import java.util.Date;
import java.util.Locale;

public class PresentationAnswer implements Out<PresentationAnswer, com.askdog.service.bo.AnswerDetail> {

    private String id;
    private String question;
    private PresentationUser answerer;
    private Content content;
    private Locale language;
    private int commentCount;
    private Date creationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public PresentationAnswer from(com.askdog.service.bo.AnswerDetail answer) {
        this.id = answer.getUuid();
        this.question = answer.getQuestion().getUuid();
        this.answerer = new PresentationUser().from(answer.getAnswerer());
        this.content = answer.getContent();
        this.language = answer.getLanguage();
        this.commentCount = answer.getComments() == null ? 0 : answer.getComments().size();
        this.creationTime = answer.getCreationTime();
        return this;
    }

}
