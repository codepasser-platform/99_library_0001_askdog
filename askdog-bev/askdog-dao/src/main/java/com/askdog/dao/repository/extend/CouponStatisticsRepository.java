package com.askdog.dao.repository.extend;

import com.askdog.model.entity.partial.TimeBasedStatistics;

import java.util.List;

public interface CouponStatisticsRepository {

    List<TimeBasedStatistics> couponRegistrationStatistics(String unit, String interval);

}
