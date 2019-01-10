package com.askdog.store.model.entity.builder;

import com.askdog.store.model.entity.Category;
import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.entity.Store;

import javax.validation.constraints.NotNull;
import java.util.Date;

public final class GoodsBuilder {

    private String name;

    private String summary;

    private long price;

    private long pointsPrice;

    private int discount;

    private long stock;

    private long sales;

    private Category category;

    private Store owner;

    public GoodsBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GoodsBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public GoodsBuilder setPrice(long price) {
        this.price = price;
        return this;
    }

    public GoodsBuilder setPointsPrice(long pointsPrice) {
        this.pointsPrice = pointsPrice;
        return this;
    }

    public GoodsBuilder setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public GoodsBuilder setStock(long stock) {
        this.stock = stock;
        return this;
    }

    public GoodsBuilder setSales(long sales) {
        this.sales = sales;
        return this;
    }

    public GoodsBuilder setCategory(String categoryId) {
        Category category = new Category();
        category.setUuid(categoryId);
        this.category = category;
        return this;
    }

    public GoodsBuilder setOwner(String storeId) {
        Store store = new Store();
        store.setUuid(storeId);
        this.owner = store;
        return this;
    }

    public Goods build() {
        Goods goods = new Goods();
        goods.setName(this.name);
        goods.setSummary(this.summary);
        goods.setPrice(this.price);
        goods.setPointsPrice(this.pointsPrice);
        goods.setDiscount(this.discount);
        goods.setStock(this.stock);
        goods.setSales(this.sales);
        goods.setCategory(this.category);
        goods.setOwner(this.owner);
        goods.setCreationTime(new Date());
        return goods;
    }

    public static GoodsBuilder goodsBuilder() {
        return new GoodsBuilder();
    }
}
