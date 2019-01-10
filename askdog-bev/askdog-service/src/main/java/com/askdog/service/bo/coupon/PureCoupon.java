package com.askdog.service.bo.coupon;

import com.askdog.model.entity.inner.coupon.CouponType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PureCoupon {

    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    private String rule;

    @NotNull
    private CouponType type;

    @NotNull
    private Long storeId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

}
