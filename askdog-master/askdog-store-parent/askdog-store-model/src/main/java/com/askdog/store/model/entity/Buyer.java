package com.askdog.store.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ad_buyer")
public class Buyer extends Owner {

    private static final long serialVersionUID = 1255074961143242823L;

    @Column(name = "buyer_id", unique = true, nullable = false, updatable = false)
    private String buyerId;

    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
