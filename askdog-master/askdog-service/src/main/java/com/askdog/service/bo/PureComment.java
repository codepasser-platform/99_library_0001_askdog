package com.askdog.service.bo;

import javax.validation.constraints.NotNull;

public class PureComment {

    @NotNull
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
