package com.askdog.service.bo;

import com.askdog.common.Out;
import com.askdog.model.entity.Label;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDetail implements Out<QuestionDetail, Question>, Serializable {

    private static final long serialVersionUID = -8479440848388895896L;

    private String uuid;
    private String subject;
    private Content content;
    private UserDetail author;
    private boolean anonymous;
    private boolean experience;
    private State state;
    private Locale language;
    private String location;
    private Date creationTime;
    private Set<String> labels = Sets.newHashSet();
    private long viewCount;
    private long followCount;
    private long upVoteCount;
    private long downVoteCount;
    private long answerCount;
    private long commentCount;
    private boolean upVoted;
    private boolean downVoted;
    private boolean followed;
    private boolean answered;
    private boolean mine;
    private List<AnswerDetail> answers = Lists.newArrayList();
    private List<CommentDetail> comments = Lists.newArrayList();
    private String qrLink;
    private boolean deletable;
    private boolean deleted;
    private AnswerDetail bestAnswer;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public UserDetail getAuthor() {
        return author;
    }

    public void setAuthor(UserDetail author) {
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

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
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

    public List<AnswerDetail> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDetail> answers) {
        this.answers = answers;
    }

    public List<CommentDetail> getComments() {
        return comments;
    }

    public void setComments(List<CommentDetail> comments) {
        this.comments = comments;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public AnswerDetail getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(AnswerDetail bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    @Override
    public QuestionDetail from(Question entity) {
        this.uuid = entity.getUuid();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.anonymous = entity.isAnonymous();
        this.experience = entity.isExperience();
        this.state = entity.getState();
        this.language = entity.getLanguage();
        this.creationTime = entity.getCreationTime();
        if (entity.getLabels() != null) {
            this.labels = entity.getLabels().stream().map(Label::getName).collect(Collectors.toSet());
        }
        return this;
    }
}
