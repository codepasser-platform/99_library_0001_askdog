package com.askdog.model.data;

import com.askdog.common.Out;
import com.askdog.model.entity.Answer;
import com.askdog.model.entity.inner.Content;

import java.util.Date;

public class BestAnswer extends Base implements Out<BestAnswer, Answer> {

    private String question;
    private Content content;
    private Date answerTime;
    private String answerer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public String getAnswerer() {
        return answerer;
    }

    public void setAnswerer(String answerer) {
        this.answerer = answerer;
    }

    @Override
    public BestAnswer from(Answer entity) {
        this.question = entity.getQuestion().getUuid();
        this.content = entity.getContent();
        this.answerer = entity.getAnswerer().getName();
        this.answerTime = entity.getCreationTime();
        return this;
    }
}
