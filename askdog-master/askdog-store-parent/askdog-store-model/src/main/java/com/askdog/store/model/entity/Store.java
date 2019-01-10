package com.askdog.store.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ad_store")
public class Store extends Owner {

    private static final long serialVersionUID = 8852303780902870671L;

    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
