package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.inner.Content;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AnswerDetail implements Out<AnswerDetail, com.askdog.service.bo.AnswerDetail> {

    private String id;
    private String question;
    private boolean isAnonymous;
    private PresentationUser answerer;
    private Content content;
    private Locale language;
    private long upVoteCount;
    private long downVoteCount;
    private boolean isUpVoted;
    private boolean isDownVoted;
    private boolean isFavorited;
    private boolean isMine;
    private Date creationTime;
    private long commentCount;
    private List<CommentDetail> comments;
    private boolean isDeletable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public PresentationUser getAnswerer() {
        return answerer;
    }

    public void setAnswerer(PresentationUser answerer) {
        this.answerer = answerer;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
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
        return isUpVoted;
    }

    public void setUpVoted(boolean upVoted) {
        isUpVoted = upVoted;
    }

    public boolean isDownVoted() {
        return isDownVoted;
    }

    public void setDownVoted(boolean downVoted) {
        isDownVoted = downVoted;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
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

    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    @Override
    public AnswerDetail from(com.askdog.service.bo.AnswerDetail answer) {
        this.id = answer.getUuid();
        if (answer.getQuestion() != null) {
            this.question = answer.getQuestion().getUuid();
        }
        this.isAnonymous = answer.isAnonymous();
        if (!answer.isAnonymous()) {
            this.answerer = new PresentationUser().from(answer.getAnswerer());
        }
        this.content = answer.getContent();
        this.language = answer.getLanguage();
        setComments(answer.getComments() == null || answer.getComments().size() == 0 ? null : answer.getComments().stream().<CommentDetail>map(answerComment -> new CommentDetail().from(answerComment)).collect(Collectors.toList()));
        this.creationTime = answer.getCreationTime();
        this.upVoteCount = answer.getUpVoteCount();
        this.downVoteCount = answer.getDownVoteCount();
        this.isUpVoted = answer.isUpVoted();
        this.isDownVoted = answer.isDownVoted();
        this.isFavorited = answer.isFavorited();
        this.isMine = answer.isMine();
        this.setCommentCount(answer.getCommentCount());
        this.setDeletable(answer.isDeletable());
        return this;
    }

}
