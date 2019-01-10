package com.askdog.store.service.bo;

import com.askdog.common.Out;
import com.askdog.store.model.entity.Order;
import com.askdog.store.model.entity.Order.Status;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDetail implements Out<OrderDetail, Order> {

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

    public OrderDetail setDelivery(DeliveryDetail delivery) {
        this.delivery = delivery;
        return this;
    }

    public List<OrderItemDetail> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDetail> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public OrderDetail from(Order entity) {
        this.id = entity.getUuid();
        this.creationTime = entity.getCreationTime();
        this.status = entity.getStatus();
        this.orderItems = entity.getOrderItems().stream().map((item) -> new OrderItemDetail().from(item)).collect(Collectors.toList());
        long totalPrice = 0;
        long totalPointPrice = 0;
        long totalDiscountPrice = 0;
        for (OrderItemDetail orderItem : orderItems) {
            totalPrice = totalPrice + (orderItem.getGoods().getPrice() * orderItem.getQuantity());
            totalPointPrice = totalPointPrice + (orderItem.getGoods().getPointsPrice() * orderItem.getQuantity());
            totalDiscountPrice = totalDiscountPrice + (orderItem.getGoods().getDiscountPrice() * orderItem.getQuantity());
        }
        this.totalPrice = totalPrice;
        this.totalPointsPrice = totalPointPrice;
        this.totalDiscountPrice = totalDiscountPrice;
        return this;
    }

}
