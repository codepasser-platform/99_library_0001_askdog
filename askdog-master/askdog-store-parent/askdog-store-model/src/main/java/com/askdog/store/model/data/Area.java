package com.askdog.store.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.annotation.Nonnull;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Area extends Base {

    protected String areaId;
    protected String areaName;
    protected Boolean display;
    protected int order;

    @Nonnull
    public abstract String getAreaId();

    public void setAreaId(@Nonnull String areaId) {
        this.areaId = areaId;
    }

    @Nonnull
    public abstract String getAreaName();

    public void setAreaName(@Nonnull String areaName) {
        this.areaName = areaName;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
