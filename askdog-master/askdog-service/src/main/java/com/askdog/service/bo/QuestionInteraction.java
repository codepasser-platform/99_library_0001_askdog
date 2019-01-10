package com.askdog.service.bo;

import java.io.Serializable;

public class QuestionInteraction implements Serializable {

    private static final long serialVersionUID = 3356986420766379286L;

    private Long viewCount;
    private Long followCount;
    private Long upVoteCount;
    private Long downVoteCount;
    private Long shareCount;
    private Double hotScore;

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
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

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public Double getHotScore() {
        return hotScore;
    }

    public void setHotScore(Double hotScore) {
        this.hotScore = hotScore;
    }
}
