package com.askdog.service.bo.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class EventTarget implements Serializable {

    private static final long serialVersionUID = -5621076602014134673L;

    @JsonFormat(shape = STRING)
    private Long id;
    private String description;
    private boolean deleted;
    private EventTarget owner;

    public EventTarget() {

    }

    public EventTarget(Long id, String description, boolean deleted) {
        this.id = id;
        this.description = description;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public EventTarget setId(Long id) {
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

    public EventTarget getOwner() {
        return owner;
    }

    public void setOwner(EventTarget owner) {
        this.owner = owner;
    }
}