package com.askdog.model.data.builder;

import com.askdog.model.data.Actions.QuestionFollow;

import java.util.Date;

public final class QuestionFollowBuilder {

    private String question;
    private String user;
    private Date followTime;

    public QuestionFollowBuilder question(String question) {
        this.question = question;
        return this;
    }

    public QuestionFollowBuilder user(String user) {
        this.user = user;
        return this;
    }

    public QuestionFollowBuilder followTime(Date followTime) {
        this.followTime = followTime;
        return this;
    }

    public QuestionFollow build() {
        QuestionFollow questionFollow = new QuestionFollow();
        questionFollow.setTarget(this.question);
        questionFollow.setUser(this.user);
        questionFollow.setTime(this.followTime != null ? this.followTime : new Date());
        return questionFollow;
    }

    public static QuestionFollowBuilder questionFollowBuilder() {
        return new QuestionFollowBuilder();
    }

}
