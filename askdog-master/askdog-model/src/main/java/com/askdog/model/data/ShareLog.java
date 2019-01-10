package com.askdog.model.data;

import com.askdog.model.data.inner.ShareType;

import java.util.Date;

public class ShareLog extends Base {

    private String userId;
    private ShareType shareType;
    private String targetId;
    private Geo geo;
    private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ShareType getShareType() {
        return shareType;
    }

    public void setShareType(ShareType shareType) {
        this.shareType = shareType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static class Geo {

        private Double lat;
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public Geo setLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public Double getLng() {
            return lng;
        }

        public Geo setLng(Double lng) {
            this.lng = lng;
            return this;
        }
    }

}
