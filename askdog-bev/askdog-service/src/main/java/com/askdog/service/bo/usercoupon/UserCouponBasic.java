package com.askdog.service.bo.usercoupon;

import com.askdog.model.entity.inner.coupon.CouponState;

import java.io.Serializable;
import java.util.Date;

public class UserCouponBasic implements Serializable {

    private static final long serialVersionUID = -1019336219326816937L;

    private Long id;
    private Long userId;
    private Long couponId;
    private Long productId;
    private CouponState state;
    private Date creationTime;
    private Date useTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CouponState getState() {
        return state;
    }

    public void setState(CouponState state) {
        this.state = state;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

}
