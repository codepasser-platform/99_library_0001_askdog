package com.askdog.web.api.vo;

import com.askdog.common.In;
import com.askdog.model.entity.inner.Content;
import com.askdog.service.bo.PureAnswer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PostedAnswer implements In<PureAnswer> {

    @NotNull
    private String questionId;

    @NotNull
    @Valid
    private Content content;

    private boolean anonymous;

    private boolean follow;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    @Override
    public PureAnswer convert() {
        PureAnswer pureAnswer = new PureAnswer();
        pureAnswer.setAnonymous(this.anonymous);
        pureAnswer.setContent(this.content);
        pureAnswer.setFollow(this.follow);
        return pureAnswer;
    }
}
