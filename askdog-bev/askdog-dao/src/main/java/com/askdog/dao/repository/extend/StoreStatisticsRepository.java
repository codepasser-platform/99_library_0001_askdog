package com.askdog.dao.repository.extend;

import com.askdog.model.entity.partial.TimeBasedStatistics;

import java.util.List;

public interface StoreStatisticsRepository {

    List<TimeBasedStatistics> storeRegistrationStatistics(String unit, String interval);

}
