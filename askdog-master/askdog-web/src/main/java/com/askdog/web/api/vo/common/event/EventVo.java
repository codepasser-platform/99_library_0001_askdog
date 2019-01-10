package com.askdog.web.api.vo.common.event;

import com.askdog.web.api.vo.common.EventUser;

public class EventVo {
    private EventUser user;
    private EventType type;
    private EventTarget owner;
    private EventTarget target;

    public EventUser getUser() {
        return user;
    }

    public EventVo setUser(EventUser user) {
        this.user = user;
        return this;
    }

    public EventType getType() {
        return type;
    }

    public EventVo setType(EventType type) {
        this.type = type;
        return this;
    }

    public EventTarget getOwner() {
        return owner;
    }

    public void setOwner(EventTarget owner) {
        this.owner = owner;
    }

    public EventTarget getTarget() {
        return target;
    }

    public EventVo setTarget(EventTarget target) {
        this.target = target;
        return this;
    }
}
