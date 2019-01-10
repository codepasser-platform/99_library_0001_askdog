package com.askdog.sync.index;

import com.askdog.model.entity.inner.State;
import com.askdog.model.entity.inner.Type;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuestionIndex {

    private String id;
    private String subject;
    private String content;
    private Integer answerCount;
    private Boolean anonymous;
    private Boolean experience;
    private Date createTime;
    private String labels;
    private State state;
    private Locale language;
    private User author;
    private Answer bestAnswer;
    private Answer newestAnswer;
    private Double answerScore;
    private Double hotScore;
    private String location;

    @JsonProperty("up_votes")
    private Long upVoteCount;

    @JsonProperty("down_votes")
    private Long downVoteCount;

    @JsonProperty("views")
    private Long viewCount;

    private Long shareCount;

    private Long followCount;

    @JsonProperty("content_all")
    private String fullAnswerContent;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Boolean getExperience() {
        return experience;
    }

    public void setExperience(Boolean experience) {
        this.experience = experience;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getFullAnswerContent() {
        return fullAnswerContent;
    }

    public void setFullAnswerContent(String fullAnswerContent) {
        this.fullAnswerContent = fullAnswerContent;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Answer getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(Answer bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    public Answer getNewestAnswer() {
        return newestAnswer;
    }

    public void setNewestAnswer(Answer newestAnswer) {
        this.newestAnswer = newestAnswer;
    }

    public Double getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(Double answerScore) {
        this.answerScore = answerScore;
    }

    public Double getHotScore() {
        return hotScore;
    }

    public void setHotScore(Double hotScore) {
        this.hotScore = hotScore;
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

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static class User {

        private String id;
        private String name;
        private String avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public abstract static class Answer {

        private String id;
        private Type type;
        private Date createTime;
        private double score;
        private boolean anonymous;
        private User answerer;
        private Long upVoteCount;
        private Long downVoteCount;
        private Long favoriteCount;
        private Long shareCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public boolean isAnonymous() {
            return anonymous;
        }

        public void setAnonymous(boolean anonymous) {
            this.anonymous = anonymous;
        }

        public User getAnswerer() {
            return answerer;
        }

        public void setAnswerer(User answerer) {
            this.answerer = answerer;
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
    }

    public static class TextAnswer extends Answer {

        private String contentText;

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }
    }

    public static class PicAnswer extends Answer {

        private List<PicItem> contentPic;

        public List<PicItem> getContentPic() {
            return contentPic;
        }

        public void setContentPic(List<PicItem> contentPic) {
            this.contentPic = contentPic;
        }
    }

    public static class PicItem {

        private String pictureUrl;
        private String description;

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
