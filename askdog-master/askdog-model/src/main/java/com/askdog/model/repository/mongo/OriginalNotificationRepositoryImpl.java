package com.askdog.model.repository.mongo;

import com.askdog.model.data.OriginalNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class OriginalNotificationRepositoryImpl implements OriginalNotificationRepositoryExtention {

    @Autowired private MongoTemplate mongoTemplate;

    @Override
    public void readAll(String userId) {
        mongoTemplate.updateMulti(Query.query(Criteria.where("recipient").is(userId)), Update.update("read", true), OriginalNotification.class);
    }
}
