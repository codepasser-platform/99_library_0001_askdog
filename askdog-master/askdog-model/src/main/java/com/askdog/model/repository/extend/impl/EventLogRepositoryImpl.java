package com.askdog.model.repository.extend.impl;

import com.askdog.model.data.EventLog;
import com.askdog.model.entity.inner.EventType;
import com.askdog.model.repository.extend.ExtendedEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class EventLogRepositoryImpl implements ExtendedEventLogRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<EventLog> topTargetByPerformerAndEventType(String performerId, EventType type, long top) {
        return mongoTemplate.aggregate(
                newAggregation(
                        match(Criteria.where("performerId").is(performerId).where("eventType").is(type.name())),
                        group("targetId")
                                .first("targetId").as("targetId")
                                .first("performerId").as("performerId")
                                .first("eventTime").as("eventTime"),
                        sort(DESC, "eventTime"),
                        project("performerId", "targetId", "eventTime").andExclude("_id"),
                        limit(top)
                ),
                "ad_event_log",
                EventLog.class).getMappedResults();
    }
}
