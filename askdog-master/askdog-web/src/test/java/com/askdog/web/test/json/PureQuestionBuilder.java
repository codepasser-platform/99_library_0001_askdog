package com.askdog.web.test.json;

import com.askdog.model.entity.inner.Content;
import com.askdog.service.bo.PureQuestion;
import com.google.common.collect.Sets;

import java.util.Set;

public class PureQuestionBuilder {

    private String subject;
    private Content content;
    private Set<String> labels;

    public PureQuestionBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public PureQuestionBuilder content(Content content) {
        this.content = content;
        return this;
    }

    public PureQuestionBuilder labels(String ... labels) {
        this.labels = Sets.newHashSet(labels);
        return this;
    }

    public PureQuestion build() {
        PureQuestion pureQuestion = new PureQuestion();
        pureQuestion.setSubject(this.subject);
        pureQuestion.setContent(this.content);
        pureQuestion.setLabels(this.labels);
        return pureQuestion;
    }

    public static PureQuestionBuilder createQuestionBuilder() {
        return new PureQuestionBuilder();
    }

}
