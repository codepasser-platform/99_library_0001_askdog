package com.askdog.model.entity.inner;

import com.askdog.common.utils.MarkdownEscaper;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TextContent extends Content<String> {

    private static final long serialVersionUID = 5133049050224019169L;

    @NotNull
    @Pattern(regexp = "([\\s\\S]{0,5000})")
    private String content;

    @JsonCreator
    public TextContent() {
        this.setType(Type.TEXT);
    }

    @JsonCreator
    public TextContent(String content) {
        this.setType(Type.TEXT);
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String textContent() {
        return MarkdownEscaper.remove(content);
    }

}
