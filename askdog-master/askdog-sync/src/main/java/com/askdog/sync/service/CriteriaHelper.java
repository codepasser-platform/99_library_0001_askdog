package com.askdog.sync.service;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Date;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public final class CriteriaHelper {

    public static Criteria timeCriteria(Date from, Date to) {
        return timeCriteria("time", from, to);
    }

    public static Criteria timeCriteria(String timeField, Date from, Date to) {
        return from == null ? where(timeField).lte(to) : where(timeField).gt(from).lte(to);
    }

}
