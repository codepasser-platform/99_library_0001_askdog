package com.askdog.model.data.builder;

import java.util.Date;

import static com.askdog.model.data.Actions.AnswerFavorite;

public final class AnswerFavoriteBuilder {

    private String question;
    private String answer;
    private String user;
    private Date favoriteTime;

    public AnswerFavoriteBuilder question(String question) {
        this.question = question;
        return this;
    }

    public AnswerFavoriteBuilder user(String user) {
        this.user = user;
        return this;
    }

    public AnswerFavoriteBuilder answer(String answer) {
        this.answer = answer;
        return this;
    }

    public AnswerFavoriteBuilder favoriteTime(Date favoriteTime) {
        this.favoriteTime = favoriteTime;
        return this;
    }

    public AnswerFavorite build() {
        AnswerFavorite answerFavorite = new AnswerFavorite();
        answerFavorite.setQuestion(this.question);
        answerFavorite.setTarget(this.answer);
        answerFavorite.setUser(this.user);
        answerFavorite.setTime(this.favoriteTime != null ? this.favoriteTime : new Date());
        return answerFavorite;
    }

    public static AnswerFavoriteBuilder questionFavoriteBuilder() {
        return new AnswerFavoriteBuilder();
    }

}
