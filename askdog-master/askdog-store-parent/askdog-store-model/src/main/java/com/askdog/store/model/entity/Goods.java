package com.askdog.store.model.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ad_goods")
public class Goods extends Base {

    private static final long serialVersionUID = 1434659590062806870L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "point_price", nullable = false)
    private long pointsPrice;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "stock", nullable = false)
    private long stock;

    @Column(name = "sales", nullable = false)
    private long sales;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private Date creationTime;

    @JoinColumn(name = "category", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Category category;

    @JoinColumn(name = "owner", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = LAZY)
    private Store owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPointsPrice() {
        return pointsPrice;
    }

    public void setPointsPrice(long pointsPrice) {
        this.pointsPrice = pointsPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Store getOwner() {
        return owner;
    }

    public void setOwner(Store owner) {
        this.owner = owner;
    }

}
