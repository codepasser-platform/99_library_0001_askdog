package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDetail implements Out<QuestionDetail, com.askdog.service.bo.QuestionDetail> {

    private String id;
    private String subject;
    private Content content;
    private boolean isAnonymous;
    private boolean experience;
    private PresentationUser author;
    private State state;
    private Locale language;
    private String location;
    private Date creationTime;
    private Set<String> labels;
    private long viewCount;
    private long upVoteCount;
    private long downVoteCount;
    private boolean upVoted;
    private boolean downVoted;
    private boolean followed;
    private boolean answered;
    private boolean mine;
    private long answerCount;
    private long commentCount;
    private List<CommentDetail> comments;
    private List<AnswerDetail> answers;
    private String qrLink;
    private boolean deletable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public boolean isExperience() {
        return experience;
    }

    public void setExperience(boolean experience) {
        this.experience = experience;
    }

    public PresentationUser getAuthor() {
        return author;
    }

    public void setAuthor(PresentationUser author) {
        this.author = author;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public long getDownVoteCount() {
        return downVoteCount;
    }

    public void setDownVoteCount(long downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public boolean isUpVoted() {
        return upVoted;
    }

    public void setUpVoted(boolean upVoted) {
        this.upVoted = upVoted;
    }

    public boolean isDownVoted() {
        return downVoted;
    }

    public void setDownVoted(boolean downVoted) {
        this.downVoted = downVoted;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public List<CommentDetail> getComments() {
        return comments;
    }

    public void setComments(List<CommentDetail> comments) {
        this.comments = comments;
    }

    public List<AnswerDetail> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDetail> answers) {
        this.answers = answers;
    }

    public String getQrLink() {
        return qrLink;
    }

    public void setQrLink(String qrLink) {
        this.qrLink = qrLink;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    @Override
    public QuestionDetail from(com.askdog.service.bo.QuestionDetail question) {
        Assert.notNull(question, "QuestionDetail cannot be null");
        setId(question.getUuid());
        setSubject(question.getSubject());
        setContent(question.getContent());
        setAnonymous(question.isAnonymous());
        setExperience(question.isExperience());
        if (!question.isAnonymous()) {
            setAuthor(new PresentationUser().from(question.getAuthor()));
        }
        setState(question.getState());
        setLanguage(question.getLanguage());
        setLocation(question.getLocation());
        setCreationTime(question.getCreationTime());
        setLabels(question.getLabels());
        setComments(question.getComments() == null ? null : question.getComments().stream().<CommentDetail>map(questionComment -> new CommentDetail().from(questionComment)).collect(Collectors.toList()));
        setAnswers(question.getAnswers() == null ? null : question.getAnswers().stream().<AnswerDetail>map(answer -> new AnswerDetail().from(answer)).collect(Collectors.toList()));
        setQrLink(question.getQrLink());

        this.viewCount = question.getViewCount();
        this.upVoteCount = question.getUpVoteCount();
        this.downVoteCount = question.getDownVoteCount();
        this.upVoted = question.isUpVoted();
        this.downVoted = question.isDownVoted();
        this.followed = question.isFollowed();
        this.answered = question.isAnswered();
        this.mine = question.isMine();
        this.setAnswerCount(question.getAnswerCount());
        this.setCommentCount(question.getCommentCount());
        this.setDeletable(question.isDeletable());

        return this;
    }
}
