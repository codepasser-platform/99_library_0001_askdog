package com.askdog.model.data.builder;

import com.askdog.model.data.EventLog;
import com.askdog.model.entity.inner.EventType;

import java.util.Date;

public class EventLogBuilder {

    private EventType eventType;
    private String performerId;
    private String ownerId;
    private String targetId;
    private Object extraData;
    private Date eventTime;

    public static EventLogBuilder getBuilder() {
        return new EventLogBuilder();
    }

    public EventLogBuilder eventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventLogBuilder performerId(String performerId) {
        this.performerId = performerId;
        return this;
    }

    public EventLogBuilder ownerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public EventLogBuilder targetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public EventLogBuilder extraData(Object extraData) {
        this.extraData = extraData;
        return this;
    }

    public EventLogBuilder eventTime(Date eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public EventLog build() {
        EventLog eventLog = new EventLog();
        eventLog.setEventType(eventType);
        eventLog.setPerformerId(performerId);
        eventLog.setOwnerId(ownerId);
        eventLog.setTargetId(targetId);
        eventLog.setExtraData(extraData);
        eventLog.setEventTime(eventTime == null ? new Date() : eventTime);
        return eventLog;
    }
}
