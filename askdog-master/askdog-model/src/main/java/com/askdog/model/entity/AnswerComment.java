package com.askdog.model.entity;

import com.askdog.model.validation.Group.CreateByApi;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_answer_comment")
public class AnswerComment extends Comment {

    private static final long serialVersionUID = -1135016491475676849L;

    @NotNull(groups = CreateByApi.class)
    @JoinColumn(name = "answer", updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
