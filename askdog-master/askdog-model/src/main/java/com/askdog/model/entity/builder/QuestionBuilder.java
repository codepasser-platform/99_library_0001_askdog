package com.askdog.model.entity.builder;

import com.askdog.model.entity.Label;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class QuestionBuilder {

    private String subject;
    private Content content;
    private User author;
    private boolean anonymous;
    private boolean share;
    private State state;
    private Locale language;
    private Date creationTime;
    private List<Label> labels;

    public static QuestionBuilder questionBuilder() {
        return new QuestionBuilder();
    }

    public QuestionBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public QuestionBuilder content(Content content) {
        this.content = content;
        return this;
    }

    public QuestionBuilder author(User author) {
        this.author = author;
        return this;
    }

    public QuestionBuilder author(String authorId) {
        User author = new User();
        author.setUuid(authorId);
        this.author = author;
        return this;
    }

    public QuestionBuilder anonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public QuestionBuilder setShare(boolean share) {
        this.share = share;
        return this;
    }

    public QuestionBuilder state(State state) {
        this.state = state;
        return this;
    }

    public QuestionBuilder language(Locale language) {
        this.language = language;
        return this;
    }

    public QuestionBuilder creationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public QuestionBuilder labels(Set<String> labelNames) {
        this.labels = LabelBuilder.of(labelNames);
        return this;
    }

    public Question build() {
        Question question = new Question();
        question.setSubject(this.subject);
        question.setContent(this.content);
        question.setAuthor(this.author);
        question.setAnonymous(this.anonymous);
        question.setExperience(this.share);
        question.setState(this.state);
        question.setLanguage(this.language);
        question.setCreationTime(this.creationTime);
        question.setLabels(this.labels);
        return question;
    }
}
