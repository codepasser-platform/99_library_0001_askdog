package com.askdog.model.data.snapshot;

import com.askdog.model.entity.inner.Content;
import com.askdog.model.entity.inner.State;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Document(collection = "ad_snapshot_question")
public class QuestionSnapshot extends Snapshot<QuestionSnapshot> {

    private String questionId;
    private String subject;
    private Content content;
    private String authorId;
    private boolean anonymous;
    private boolean share;
    private State state;
    private Locale language;
    private String location;
    private Date creationTime;
    private Set<String> labelNames;
    private long viewCount;
    private long followCount;
    private long upVoteCount;
    private long downVoteCount;
    private List<String> answerIds;
    private List<String> commentIds;
    private String qrLink;
    private String bestAnswerId;

    public String getQuestionId() {
        return questionId;
    }

    public QuestionSnapshot setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public QuestionSnapshot setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public QuestionSnapshot setContent(Content content) {
        this.content = content;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public QuestionSnapshot setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public QuestionSnapshot setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        return this;
    }

    public boolean isShare() {
        return share;
    }

    public QuestionSnapshot setShare(boolean share) {
        this.share = share;
        return this;
    }

    public State getState() {
        return state;
    }

    public QuestionSnapshot setState(State state) {
        this.state = state;
        return this;
    }

    public Locale getLanguage() {
        return language;
    }

    public QuestionSnapshot setLanguage(Locale language) {
        this.language = language;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public QuestionSnapshot setLocation(String location) {
        this.location = location;
        return this;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public QuestionSnapshot setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public Set<String> getLabelNames() {
        return labelNames;
    }

    public QuestionSnapshot setLabelNames(Set<String> labelNames) {
        this.labelNames = labelNames;
        return this;
    }

    public long getViewCount() {
        return viewCount;
    }

    public QuestionSnapshot setViewCount(long viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public long getFollowCount() {
        return followCount;
    }

    public QuestionSnapshot setFollowCount(long followCount) {
        this.followCount = followCount;
        return this;
    }

    public long getUpVoteCount() {
        return upVoteCount;
    }

    public QuestionSnapshot setUpVoteCount(long upVoteCount) {
        this.upVoteCount = upVoteCount;
        return this;
    }

    public long getDownVoteCount() {
        return downVoteCount;
    }

    public QuestionSnapshot setDownVoteCount(long downVoteCount) {
        this.downVoteCount = downVoteCount;
        return this;
    }

    public List<String> getAnswerIds() {
        return answerIds;
    }

    public QuestionSnapshot setAnswerIds(List<String> answerIds) {
        this.answerIds = answerIds;
        return this;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public QuestionSnapshot setCommentIds(List<String> commentIds) {
        this.commentIds = commentIds;
        return this;
    }

    public String getQrLink() {
        return qrLink;
    }

    public QuestionSnapshot setQrLink(String qrLink) {
        this.qrLink = qrLink;
        return this;
    }

    public String getBestAnswerId() {
        return bestAnswerId;
    }

    public QuestionSnapshot setBestAnswerId(String bestAnswerId) {
        this.bestAnswerId = bestAnswerId;
        return this;
    }
}
