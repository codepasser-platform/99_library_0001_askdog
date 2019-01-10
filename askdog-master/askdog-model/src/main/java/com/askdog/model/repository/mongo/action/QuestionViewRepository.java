package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.Actions.QuestionView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionViewRepository extends MongoRepository<QuestionView, String>, QuestionViewRepositoryExtention {
    long countByTarget(String target);
}
