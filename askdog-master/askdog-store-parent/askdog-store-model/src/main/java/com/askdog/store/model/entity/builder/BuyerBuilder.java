package com.askdog.store.model.entity.builder;

import com.askdog.store.model.entity.Buyer;

import java.util.Date;

public final class BuyerBuilder {

    private String buyerId;

    private String name;

    public BuyerBuilder setBuyerId(String buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public BuyerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Buyer build() {
        Buyer buyer = new Buyer();
        buyer.setBuyerId(this.buyerId);
        buyer.setName(this.name);
        buyer.setCreationTime(new Date());
        return buyer;
    }

    public static BuyerBuilder buyerBuilder() {
        return new BuyerBuilder();
    }
}
