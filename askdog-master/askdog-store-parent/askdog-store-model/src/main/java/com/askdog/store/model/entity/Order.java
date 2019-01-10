package com.askdog.store.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_order")
public class Order extends Base {

    private static final long serialVersionUID = 7493969125614941150L;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    @JoinColumn(name = "owner", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Buyer owner;

    @OneToMany(mappedBy = "owner", fetch = LAZY, cascade = REMOVE)
    private List<OrderItem> orderItems;

    public enum Status {
        CANCELED, CONFIRMED, PAYMENT, SHIPPED, RECEIPTED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Buyer getOwner() {
        return owner;
    }

    public void setOwner(Buyer owner) {
        this.owner = owner;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
