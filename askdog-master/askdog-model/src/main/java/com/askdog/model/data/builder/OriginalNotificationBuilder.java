package com.askdog.model.data.builder;

import com.askdog.model.data.OriginalNotification;
import com.askdog.model.data.inner.NotificationType;

import java.util.Date;

public final class OriginalNotificationBuilder {

    private NotificationType notificationType;
    private String recipientUserId;
    private String refLogId;

    public OriginalNotificationBuilder notificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
        return this;
    }

    public OriginalNotificationBuilder recipientUserId(String recipientUserId) {
        this.recipientUserId = recipientUserId;
        return this;
    }

    public OriginalNotificationBuilder refLogId(String refLogId) {
        this.refLogId = refLogId;
        return this;
    }

    public OriginalNotification build() {
        OriginalNotification notification = new OriginalNotification();
        notification.setNotificationType(this.notificationType);
        notification.setRecipient(this.recipientUserId);
        notification.setRefLogId(this.refLogId);
        notification.setRead(false);
        notification.setCreateTime(new Date());
        return notification;
    }

    public static OriginalNotificationBuilder notificationBuilder() {
        return new OriginalNotificationBuilder();
    }
}
