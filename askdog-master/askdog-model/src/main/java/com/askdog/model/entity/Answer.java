package com.askdog.model.entity;

import com.askdog.model.converter.ContentConverter;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.validation.Group;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_answer")
public class Answer extends Base {

    private static final long serialVersionUID = 4525859336160058408L;

    @NotNull
    @JoinColumn(name = "question", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Question question;

    @NotNull
    @Column(name = "content", nullable = false, length = 5028)
    @Convert(converter = ContentConverter.class)
    @Valid
    private Content content;

    @NotNull(groups = {Group.Create.class, Group.Edit.class})
    @JoinColumn(name = "answerer", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private User answerer;

    @Column(name = "anonymous", nullable = false)
    private boolean anonymous;

    @NotNull(groups = {Group.Create.class, Group.Edit.class})
    @Column(name = "language", nullable = false)
    private Locale language;

    @NotNull(groups = {Group.Create.class, Group.Edit.class})
    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    @OrderBy(value = "creationTime DESC")
    @OneToMany(mappedBy = "answer", fetch = LAZY, cascade = REMOVE)
    private List<AnswerComment> comments;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getAnswerer() {
        return answerer;
    }

    public void setAnswerer(User answerer) {
        this.answerer = answerer;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public List<AnswerComment> getComments() {
        return comments;
    }

    public void setComments(List<AnswerComment> comments) {
        this.comments = comments;
    }
}
