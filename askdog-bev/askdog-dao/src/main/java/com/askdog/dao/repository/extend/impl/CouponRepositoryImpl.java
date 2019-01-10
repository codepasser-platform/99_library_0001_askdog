package com.askdog.dao.repository.extend.impl;

import com.askdog.dao.repository.extend.CouponStatisticsRepository;
import com.askdog.model.entity.partial.TimeBasedStatistics;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CouponRepositoryImpl implements CouponStatisticsRepository {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<TimeBasedStatistics> couponRegistrationStatistics(String unit, String interval) {
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT date_trunc('" + unit + "', join_u_eu.u_creation_time) AS day , count(*) AS coupon_count " +
                        "FROM (" +
                        "   SELECT" +
                        "       u.id," +
                        "       CASE" +
                        "           WHEN u.creation_time IS NULL THEN eu.registration_time" +
                        "           ELSE u.creation_time" +
                        "       END" +
                        "       AS u_creation_time" +
                        "   FROM mc_user_coupon u LEFT JOIN mc_external_user eu ON u.id = eu.bind_user" +
                        ") AS join_u_eu " +
                        "WHERE join_u_eu.u_creation_time > now() - interval '" + interval + "' " +
                        "GROUP BY 1 " +
                        "ORDER BY 1",
                "CouponCreationInWeek"
        );
        return nativeQuery.getResultList();
    }
}
