package com.askdog.model.data;

import com.askdog.model.data.inner.NotificationType;

import java.util.Date;

public class OriginalNotification extends Base {

    private NotificationType notificationType;
    private String recipient;
    private String refLogId;
    private boolean read;
    private Date createTime;

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRefLogId() {
        return refLogId;
    }

    public void setRefLogId(String refLogId) {
        this.refLogId = refLogId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
