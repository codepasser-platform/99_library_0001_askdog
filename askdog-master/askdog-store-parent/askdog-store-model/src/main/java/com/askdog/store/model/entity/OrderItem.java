package com.askdog.store.model.entity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_order_item")
public class OrderItem extends Base {

    private static final long serialVersionUID = -5363877884510615227L;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @JoinColumn(name = "goods", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Goods goods;

    @JoinColumn(name = "owner", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Order owner;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Order getOwner() {
        return owner;
    }

    public void setOwner(Order owner) {
        this.owner = owner;
    }

}
