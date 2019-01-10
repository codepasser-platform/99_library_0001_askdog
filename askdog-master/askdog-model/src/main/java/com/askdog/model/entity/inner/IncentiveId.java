package com.askdog.model.entity.inner;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class IncentiveId implements Serializable {

    private static final long serialVersionUID = -2455979583283759969L;

    private EventType eventType;
    private IncentiveType incentiveType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public IncentiveType getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
    }
}
