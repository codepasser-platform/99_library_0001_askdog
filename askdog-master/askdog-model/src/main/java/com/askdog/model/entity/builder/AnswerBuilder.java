package com.askdog.model.entity.builder;

import com.askdog.model.entity.Answer;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.Content;

import java.util.Date;
import java.util.Locale;

public final class AnswerBuilder {

    private Question question;
    private Content content;
    private User answerer;
    private boolean anonymous;
    private Locale language;
    private Date creationTime;

    public AnswerBuilder question(Question question) {
        this.question = question;
        return this;
    }

    public AnswerBuilder question(String questionId) {
        Question question = new Question();
        question.setUuid(questionId);
        this.question = question;
        return this;
    }

    public AnswerBuilder content(Content content) {
        this.content = content;
        return this;
    }

    public AnswerBuilder answerer(User answerer) {
        this.answerer = answerer;
        return this;
    }

    public AnswerBuilder answerer(String answererId) {
        User user = new User();
        user.setUuid(answererId);
        this.answerer = user;
        return this;
    }

    public AnswerBuilder anonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public AnswerBuilder language(Locale language) {
        this.language = language;
        return this;
    }

    public AnswerBuilder creationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public Answer build() {
        Answer answer = new Answer();
        answer.setQuestion(this.question);
        answer.setContent(this.content);
        answer.setAnswerer(this.answerer);
        answer.setAnonymous(this.anonymous);
        answer.setLanguage(this.language);
        answer.setCreationTime(this.creationTime);
        return answer;
    }

    public static AnswerBuilder answerBuilder() {
        return new AnswerBuilder();
    }
}
