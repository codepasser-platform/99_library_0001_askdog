package com.askdog.model.data.builder;

import com.askdog.model.data.ShareLog;
import com.askdog.model.data.ShareLog.Geo;
import com.askdog.model.data.inner.ShareType;

import java.util.Date;

public class ShareLogBuilder {

    private String userId;
    private ShareType shareType;
    private String targetId;
    private Geo geo;
    private Date createTime = new Date();

    public ShareLogBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public ShareLogBuilder shareType(ShareType shareType) {
        this.shareType = shareType;
        return this;
    }

    public ShareLogBuilder targetId(String targetId) {
        this.targetId = targetId;
        return this;
    }


    public ShareLogBuilder geo(Geo geo) {
        this.geo = geo;
        return this;
    }


    public ShareLogBuilder createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static ShareLogBuilder builder() {
        return new ShareLogBuilder();
    }

    public ShareLog build() {
        ShareLog shareLog = new ShareLog();
        shareLog.setUserId(userId);
        shareLog.setShareType(shareType);
        shareLog.setTargetId(targetId);
        shareLog.setGeo(geo);
        shareLog.setCreateTime(createTime);
        return shareLog;
    }


}
