package com.askdog.service.bo;

import com.askdog.model.entity.Answer;

import javax.annotation.Nonnull;

public class AnswerInteraction implements Comparable<AnswerInteraction> {

    private Answer answer;
    private Long upVoteCount;
    private Long downVoteCount;
    private Long favoriteCount;
    private Long shareCount;
    private double score;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Long getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(Long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public Long getDownVoteCount() {
        return downVoteCount;
    }

    public void setDownVoteCount(Long downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(@Nonnull AnswerInteraction answerInteraction) {
        return Double.compare(this.getScore(), answerInteraction.getScore());
    }
}
