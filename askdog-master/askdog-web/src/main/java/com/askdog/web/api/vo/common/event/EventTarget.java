package com.askdog.web.api.vo.common.event;

public class EventTarget {
    private String id;
    private String description;
    private boolean deleted;

    public String getId() {
        return id;
    }

    public EventTarget setId(String id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventTarget setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public EventTarget setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}