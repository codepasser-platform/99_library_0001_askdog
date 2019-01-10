package com.askdog.service.bo;

import com.askdog.model.entity.inner.Content;

import javax.validation.constraints.NotNull;

public class PureAnswer {

    @NotNull
    private Content content;

    private boolean anonymous;

    private boolean follow;

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
}
