package com.askdog.store.model.data;


import com.askdog.store.model.data.inner.TargetType;

import javax.validation.constraints.NotNull;

public abstract class Target extends Base {

    private String target;

    private TargetType targetType;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }
}
