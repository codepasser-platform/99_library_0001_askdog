package com.askdog.model.data.builder;

import com.askdog.model.data.Actions;
import com.askdog.model.data.inner.TargetType;

import java.util.Date;

@SuppressWarnings("unchecked")
abstract class ActionBuilder<B extends ActionBuilder<B, E>, E extends Actions.Action> {

    private String user;
    private String target;
    private TargetType targetType;
    private Date time;

    public B user(String user) {
        this.user = user;
        return (B) this;
    }

    public B target(String target) {
        this.target = target;
        return (B) this;
    }

    public B targetType(TargetType targetType) {
        this.targetType = targetType;
        return (B) this;
    }

    public B time(Date time) {
        this.time = time;
        return (B) this;
    }

    public E build(E action) {
        action.setUser(this.user);
        action.setTarget(this.target);
        action.setTargetType(this.targetType);
        action.setTime(this.time != null ? this.time : new Date());
        return action;
    }

}
