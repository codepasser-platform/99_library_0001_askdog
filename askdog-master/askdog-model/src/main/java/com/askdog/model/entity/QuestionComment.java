package com.askdog.model.entity;

import com.askdog.model.validation.Group;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_question_comment")
public class QuestionComment extends Comment {

    private static final long serialVersionUID = -3704490911403959932L;

    @NotNull(groups = Group.CreateByApi.class)
    @JoinColumn(name = "question", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
