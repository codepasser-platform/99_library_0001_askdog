package com.askdog.store.service.bo;

import com.askdog.common.Out;
import com.askdog.store.model.data.Sku.SkuItem;
import com.askdog.store.model.entity.Goods;

import javax.annotation.Nonnull;
import java.util.List;

public class GoodsDetail implements Out<GoodsDetail, Goods> {

    private String id;

    private String name;

    private String summary;

    private long price;

    private long pointsPrice;

    private int discount;

    private long discountPrice;

    private long stock;

    private long sales;

    private List<String> avatars;

    private List<String> pictures;

    private List<SkuItem> skuItems;

    @Override
    public GoodsDetail from(@Nonnull Goods entity) {
        this.id = entity.getUuid();
        this.name = entity.getName();
        this.summary = entity.getSummary();
        this.price = entity.getPrice() / 100L;
        this.pointsPrice = entity.getPointsPrice();
        this.discountPrice = (entity.getPrice() * (entity.getDiscount() / 10)) / 100L;
        this.discount = entity.getDiscount();
        this.stock = entity.getStock();
        this.sales = entity.getSales();
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(long discountPrice) {
        this.discountPrice = discountPrice;
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

    public List<String> getAvatars() {
        return avatars;
    }

    public GoodsDetail setAvatars(List<String> avatars) {
        this.avatars = avatars;
        return this;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public GoodsDetail setPictures(List<String> pictures) {
        this.pictures = pictures;
        return this;
    }

    public List<SkuItem> getSkuItems() {
        return skuItems;
    }

    public GoodsDetail setSkuItems(List<SkuItem> skuItems) {
        this.skuItems = skuItems;
        return this;
    }
}
