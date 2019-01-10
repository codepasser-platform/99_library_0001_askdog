package com.askdog.store.web.api.vo;

import com.askdog.common.Out;
import com.askdog.store.model.entity.Order;
import com.askdog.store.model.entity.Order.Status;
import com.askdog.store.service.bo.DeliveryDetail;
import com.askdog.store.service.bo.OrderDetail;
import com.askdog.store.service.bo.OrderItemDetail;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

public class PresentationOrder implements Out<PresentationOrder, OrderDetail> {

    // TODO: Order:goods = 1:1 then set goods's first avatar
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String id;

    private long totalPrice;

    private long totalPointsPrice;

    private long totalDiscountPrice;

    private Date creationTime;

    private Status status;

    private DeliveryDetail delivery;

    private List<OrderItemDetail> orderItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTotalPointsPrice() {
        return totalPointsPrice;
    }

    public void setTotalPointsPrice(long totalPointsPrice) {
        this.totalPointsPrice = totalPointsPrice;
    }

    public long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }

    public void setTotalDiscountPrice(long totalDiscountPrice) {
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DeliveryDetail getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryDetail delivery) {
        this.delivery = delivery;
    }

    public List<OrderItemDetail> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDetail> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public PresentationOrder from(OrderDetail detail) {
        this.id = detail.getId();
        this.status = detail.getStatus();
        this.creationTime = detail.getCreationTime();
        this.totalPrice = detail.getTotalPrice();
        this.totalPointsPrice = detail.getTotalPointsPrice();
        this.totalDiscountPrice = detail.getTotalDiscountPrice();

        this.delivery = detail.getDelivery();
        this.orderItems = detail.getOrderItems();

        // TODO: Order:goods = 1:1 then set goods's first avatar
        this.avatar = detail.getAvatar();

        return this;
    }
}
