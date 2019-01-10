package com.askdog.service.bo.admin.dashboard;

import com.askdog.model.entity.partial.TimeBasedStatistics;

import java.util.List;

public class CouponStatistics {

    private Long totalCouponCount;
    private List<TimeBasedStatistics> couponCreationTrend;

    public Long getTotalCouponCount() {
        return totalCouponCount;
    }

    public void setTotalCouponCount(Long totalCouponCount) {
        this.totalCouponCount = totalCouponCount;
    }

    public List<TimeBasedStatistics> getCouponCreationTrend() {
        return couponCreationTrend;
    }

    public void setCouponCreationTrend(List<TimeBasedStatistics> couponCreationTrend) {
        this.couponCreationTrend = couponCreationTrend;
    }
}
