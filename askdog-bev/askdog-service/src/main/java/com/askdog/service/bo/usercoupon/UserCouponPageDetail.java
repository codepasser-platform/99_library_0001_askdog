package com.askdog.service.bo.usercoupon;

import com.askdog.model.entity.inner.coupon.CouponState;
import com.askdog.model.entity.inner.coupon.CouponType;
import com.askdog.service.bo.coupon.CouponDetail;
import com.askdog.service.bo.coupon.CouponDetailBasic;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class UserCouponPageDetail {

    @JsonFormat(shape = STRING)
    private long id;
    private String name;
    private Date receiveTime;
    private String rule;
    private CouponState couponState;
    private CouponType type;
    private StoreBasic storeBasic;

    public UserCouponPageDetail from(UserCouponDetail userCouponDetail) {
        CouponDetail couponDetail = userCouponDetail.getCouponDetail();
        this.id = userCouponDetail.getId();
        this.name = couponDetail.getName();
        this.receiveTime = userCouponDetail.getCreationTime();
        this.rule = couponDetail.getCouponRule();
        this.couponState = userCouponDetail.getCouponState();
        this.type = couponDetail.getType();
        this.storeBasic = new StoreBasic().from(userCouponDetail.getStoreDetail());
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public CouponState getCouponState() {
        return couponState;
    }

    public void setCouponState(CouponState couponState) {
        this.couponState = couponState;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public StoreBasic getStoreBasic() {
        return storeBasic;
    }

    public void setStoreBasic(StoreBasic storeBasic) {
        this.storeBasic = storeBasic;
    }

}
