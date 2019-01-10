package com.askdog.service.bo;

import com.askdog.model.entity.inner.Content;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class PureQuestion {

    @NotNull
    private String subject;

    @Valid
    @NotNull
    private Content content;

    @Size(max = 5)
    private Set<String> labels;

    private String ip;

    private Location location;

    private boolean anonymous;

    private boolean follow;

    @Valid
    private ShareAnswer shareAnswer;

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

    public Set<String> getLabels() {
        return labels;
    }

    public void setLabels(Set<String> labels) {
        this.labels = labels;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ShareAnswer getShareAnswer() {
        return shareAnswer;
    }

    public void setShareAnswer(ShareAnswer shareAnswer) {
        this.shareAnswer = shareAnswer;
    }

    public class Location {

        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public class ShareAnswer {

        @NotNull
        private Content content;

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }
}
