package com.askdog.service.bo;

import com.askdog.model.entity.inner.Content;
import com.askdog.service.bo.EditQuestion.EditAbleShareAnswer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class AmendedQuestion {

    @NotNull
    private String subject;

    @NotNull
    @Valid
    private Content content;

    @Size(max = 5)
    private Set<String> labels;

    private boolean anonymous;

    private boolean follow;

    @Valid
    private EditAbleShareAnswer shareAnswer;

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

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
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

    public EditAbleShareAnswer getShareAnswer() {
        return shareAnswer;
    }

    public void setShareAnswer(EditAbleShareAnswer shareAnswer) {
        this.shareAnswer = shareAnswer;
    }
}
