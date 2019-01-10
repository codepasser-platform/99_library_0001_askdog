package com.askdog.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_label")
public class Label extends Base {

    private static final long serialVersionUID = -4145514723195780582L;

    @NotNull
    @Column(name = "name", nullable = false, unique = true, length = 20)
    @Size(min = 1, max = 20)
    private String name;

    @ManyToMany(mappedBy = "labels", targetEntity = Question.class, fetch = LAZY)
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
