package com.askdog.store.model.data;

import org.springframework.data.annotation.Id;

public abstract class Base {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}