package com.askdog.model.entity;

import com.askdog.model.converter.ContentConverter;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import com.askdog.model.validation.Group.Create;
import com.askdog.model.validation.Group.Edit;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_question")
public class Question extends Base {

    private static final long serialVersionUID = 5365338075419049448L;

    @NotNull
    @Pattern(regexp = "(^.{1,150}$)")
    @Column(name = "subject", nullable = false, length = 150)
    private String subject;

    @NotNull
    @Column(name = "content", length = 5028)
    @Convert(converter = ContentConverter.class)
    @Valid
    private Content content;

    @NotNull(groups = {Create.class, Edit.class})
    @JoinColumn(name = "author", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private User author;

    @Column(name = "anonymous", nullable = false)
    private boolean anonymous;

    @Column(name = "experience", nullable = false)
    private boolean experience;

    @NotNull(groups = {Create.class, Edit.class})
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @NotNull(groups = {Create.class, Edit.class})
    @Column(name = "language", nullable = false)
    private Locale language;

    @NotNull(groups = {Create.class, Edit.class})
    @Column(name = "creation_time", nullable = false)
    private Date creationTime;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = LAZY, targetEntity = Label.class)
    @JoinTable(
            name = "ad_question_label",
            joinColumns = {@JoinColumn(name = "question", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "label", referencedColumnName = "uuid")}
    )
    @Valid
    @Size(max = 5)
    private List<Label> labels;

    @OrderBy(value = "creationTime ASC")
    @OneToMany(mappedBy = "question", fetch = LAZY, cascade = {MERGE, REMOVE}, orphanRemoval = true)
    private List<Answer> answers;

    @OrderBy(value = "creationTime DESC")
    @OneToMany(mappedBy = "question", fetch = LAZY, cascade = REMOVE, orphanRemoval = true)
    private List<QuestionComment> comments;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<QuestionComment> getComments() {
        return comments;
    }

    public void setComments(List<QuestionComment> comments) {
        this.comments = comments;
    }

}
