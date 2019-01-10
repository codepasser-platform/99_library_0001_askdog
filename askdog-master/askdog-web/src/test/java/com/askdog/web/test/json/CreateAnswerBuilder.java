package com.askdog.web.test.json;

import com.askdog.model.entity.inner.Content;
import com.askdog.web.api.vo.PostedAnswer;

public class CreateAnswerBuilder {

    private String questionId;
    private Content content;

    public CreateAnswerBuilder questionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public CreateAnswerBuilder content(Content content) {
        this.content = content;
        return this;
    }

    public PostedAnswer build() {
        PostedAnswer createAnswer = new PostedAnswer();
        createAnswer.setQuestionId(this.questionId);
        createAnswer.setContent(this.content);
        return createAnswer;
    }

    public static CreateAnswerBuilder createAnswerBuilder() {
        return new CreateAnswerBuilder();
    }
}
