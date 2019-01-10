package com.askdog.store.web.api.vo;

import com.askdog.common.Out;
import com.askdog.store.model.data.Sku;
import com.askdog.store.service.bo.GoodsDetail;

import java.util.List;

public class PresentationGoods implements Out<PresentationGoods, GoodsDetail> {

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

    private List<Sku.SkuItem> skuItems;

    @Override
    public PresentationGoods from(GoodsDetail detail) {
        this.id = detail.getId();
        this.name = detail.getName();
        this.summary = detail.getSummary();
        this.price = detail.getPrice();
        this.pointsPrice = detail.getPointsPrice();
        this.discount = detail.getDiscount();
        this.discountPrice = detail.getDiscountPrice();
        this.stock = detail.getStock();
        this.sales = detail.getSales();
        this.avatars = detail.getAvatars();
        this.pictures = detail.getPictures();
        this.skuItems = detail.getSkuItems();
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

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<Sku.SkuItem> getSkuItems() {
        return skuItems;
    }

    public void setSkuItems(List<Sku.SkuItem> skuItems) {
        this.skuItems = skuItems;
    }
}
