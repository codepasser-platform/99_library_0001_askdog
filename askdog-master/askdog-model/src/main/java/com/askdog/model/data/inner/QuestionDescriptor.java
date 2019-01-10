package com.askdog.model.data.inner;

import java.util.Date;

public class QuestionDescriptor {

    private String uuid;
    private String subject;
    private Date creationTime;
    private long viewCount;
    private long sharedCount;
    private long upVoteCount;

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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(long sharedCount) {
        this.sharedCount = sharedCount;
    }

    public long getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(long upVoteCount) {
        this.upVoteCount = upVoteCount;
    }
}
