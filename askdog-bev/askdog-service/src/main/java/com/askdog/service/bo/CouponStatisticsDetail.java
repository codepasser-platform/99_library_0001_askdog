package com.askdog.service.bo;

import com.askdog.model.entity.Coupon;
import com.askdog.model.entity.inner.coupon.CouponType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public class CouponStatisticsDetail {

    @JsonFormat(shape = STRING)
    private Long id;
    private String name;
    private String rule;
    private Long useCount;
    private Long notUseCount;
    private Date creationTime;
    private CouponType type;


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

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }

    public Long getNotUseCount() {
        return notUseCount;
    }

    public void setNotUseCount(Long notUseCount) {
        this.notUseCount = notUseCount;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }


    public CouponStatisticsDetail from(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.rule = coupon.getRule();
        this.creationTime = coupon.getCreationTime();
        this.type = coupon.getType();
        return this;
    }

}
