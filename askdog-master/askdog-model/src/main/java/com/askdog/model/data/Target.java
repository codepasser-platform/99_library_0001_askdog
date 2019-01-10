package com.askdog.model.data;

import com.askdog.model.data.inner.TargetType;

import javax.validation.constraints.NotNull;

public abstract class Target extends Base {

    @NotNull
    private String target;

    @NotNull
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
