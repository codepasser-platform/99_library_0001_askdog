package com.askdog.web.api.vo.common;

public class EventUser {
    private String id;
    private String username;

    public String getId() {
        return id;
    }

    public EventUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public EventUser setUsername(String username) {
        this.username = username;
        return this;
    }
}