package com.askdog.store.model.entity.builder;

import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.entity.Order;
import com.askdog.store.model.entity.OrderItem;

import javax.validation.constraints.NotNull;

public final class OrderItemBuilder {


    private int quantity;

    private Goods goods;

    private Order owner;

    public OrderItemBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItemBuilder setGoods(String goodsId) {
        Goods goods = new Goods();
        goods.setUuid(goodsId);
        this.goods = goods;
        return this;
    }

    public OrderItemBuilder setOwner(String orderId) {
        Order order = new Order();
        order.setUuid(orderId);
        this.owner = order;
        return this;
    }

    public OrderItem build() {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(this.quantity);
        orderItem.setGoods(this.goods);
        orderItem.setOwner(this.owner);
        return orderItem;
    }

    public static OrderItemBuilder orderItemBuilder() {
        return new OrderItemBuilder();
    }
}
