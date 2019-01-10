package com.askdog.store.service.bo;

import com.askdog.common.Out;
import com.askdog.store.model.entity.OrderItem;

public class OrderItemDetail implements Out<OrderItemDetail, OrderItem> {

    private String id;

    private int quantity;

    private GoodsDetail goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GoodsDetail getGoods() {
        return goods;
    }

    public void setGoods(GoodsDetail goods) {
        this.goods = goods;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public OrderItemDetail from(OrderItem entity) {
        this.id = entity.getUuid();
        this.quantity = entity.getQuantity();
        this.goods = new GoodsDetail().from(entity.getGoods());
        return this;
    }
}
