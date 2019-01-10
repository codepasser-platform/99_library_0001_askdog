package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.Actions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionFollowRepository extends MongoRepository<Actions.QuestionFollow, String> {

    int countByUserAndTarget(String user, String question);

    long countByTarget(String question);

    int deleteByUserAndTarget(String user, String question);

    Page<Actions.QuestionFollow> findByUser(String user, Pageable pageable);

    List<Actions.QuestionFollow> findAllByTarget(String question);
}
