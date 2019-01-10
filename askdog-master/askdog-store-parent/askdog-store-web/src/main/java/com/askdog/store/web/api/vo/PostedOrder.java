package com.askdog.store.web.api.vo;

import com.askdog.common.In;
import com.askdog.store.service.bo.PureOrder;

import javax.annotation.Nonnull;

public class PostedOrder implements In<PureOrder> {

    private String deliveryId;

    private int quantity;

    private String goodsId;

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Nonnull
    @Override
    public PureOrder convert() {
        PureOrder pureOrder = new PureOrder();
        pureOrder.setDeliveryId(this.deliveryId);
        pureOrder.setQuantity(this.quantity);
        pureOrder.setGoodsId(this.goodsId);
        return pureOrder;
    }
}
