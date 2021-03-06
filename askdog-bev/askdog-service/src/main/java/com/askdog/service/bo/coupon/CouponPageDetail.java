package com.askdog.service.bo.coupon;

import com.askdog.model.entity.Coupon;
import com.askdog.model.entity.inner.coupon.CouponType;
import com.askdog.service.bo.usercoupon.StoreBasic;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class CouponPageDetail {

    @JsonFormat(shape = STRING)
    private Long id;
    private String name;
    private String rule;
    private CouponType type;
    private Date creationTime;
    private StoreBasic storeBasic;

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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public StoreBasic getStoreBasic() {
        return storeBasic;
    }

    public void setStoreBasic(StoreBasic storeBasic) {
        this.storeBasic = storeBasic;
    }

    public CouponPageDetail from(CouponDetail couponDetail) {
        this.id = couponDetail.getId();
        this.name = couponDetail.getName();
        this.rule = couponDetail.getCouponRule();
        this.type = couponDetail.getType();
        this.creationTime = couponDetail.getCreationTime();
        this.storeBasic = new StoreBasic().from(couponDetail.getStore());
        return this;
    }

}
