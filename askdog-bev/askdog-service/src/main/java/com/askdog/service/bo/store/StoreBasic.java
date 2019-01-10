package com.askdog.service.bo.store;

import com.askdog.common.Out;
import com.askdog.service.bo.product.productdetail.ProductPageDetail_Coupon;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class StoreBasic implements Out<StoreBasic, StoreDetail> {

    @JsonFormat(shape = STRING)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String coverImage;
    private String type;
    private Float cpc;
    private List<ProductPageDetail_Coupon> coupons = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCpc() {
        return cpc;
    }

    public void setCpc(Float cpc) {
        this.cpc = cpc;
    }

    public List<ProductPageDetail_Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<ProductPageDetail_Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public StoreBasic from(StoreDetail storeDetail) {
        this.id = storeDetail.getId();
        this.name = storeDetail.getName();
        this.address = storeDetail.getAddress();
        this.phone = storeDetail.getPhone();
        this.coverImage = storeDetail.getCoverImage();
        this.type = storeDetail.getType();
        this.cpc = storeDetail.getCpc();
        if(null != storeDetail.getSpecialProduct()) {
            this.coupons = storeDetail.getSpecialProduct().getCoupons();
        }
        return this;
    }
}
