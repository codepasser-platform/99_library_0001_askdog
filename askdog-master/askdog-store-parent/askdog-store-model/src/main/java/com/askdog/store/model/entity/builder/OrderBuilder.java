package com.askdog.store.model.entity.builder;

import com.askdog.store.model.entity.Buyer;
import com.askdog.store.model.entity.Order;
import com.askdog.store.model.entity.Order.Status;

import java.util.Date;

public final class OrderBuilder {

    private Status status;

    private Buyer owner;

    public OrderBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setOwner(String buyerId) {
        Buyer buyer = new Buyer();
        buyer.setUuid(buyerId);
        this.owner = buyer;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setStatus(this.status);
        order.setOwner(this.owner);
        order.setCreationTime(new Date());
        return order;
    }

    public static OrderBuilder orderBuilder() {
        return new OrderBuilder();
    }
}
