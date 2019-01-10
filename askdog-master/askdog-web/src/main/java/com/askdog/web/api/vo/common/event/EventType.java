package com.askdog.web.api.vo.common.event;

public class EventType {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public EventType setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventType setName(String name) {
        this.name = name;
        return this;
    }
}
