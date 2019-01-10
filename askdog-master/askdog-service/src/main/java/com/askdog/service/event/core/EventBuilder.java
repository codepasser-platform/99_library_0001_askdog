package com.askdog.service.event.core;

import com.askdog.model.entity.inner.EventType;

import java.util.Date;

public class EventBuilder {

    private EventType eventType;
    private String performerId;
    private String targetId;
    private Date eventTime = new Date();

    public static EventBuilder getBuilder() {
        return new EventBuilder();
    }

    public EventBuilder setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public EventBuilder setPerformerId(String performerId) {
        this.performerId = performerId;
        return this;
    }

    public EventBuilder setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public EventBuilder setEventTime(Date eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public Event build() {
        Event event = new Event();
        event.setEventType(eventType);
        event.setPerformerId(performerId);
        event.setTargetId(targetId);
        event.setEventTime(eventTime);
        return event;
    }
}
