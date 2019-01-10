package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.Actions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionShareRepository extends MongoRepository<Actions.QuestionShare, String> {
    long countByTarget(String target);
}
