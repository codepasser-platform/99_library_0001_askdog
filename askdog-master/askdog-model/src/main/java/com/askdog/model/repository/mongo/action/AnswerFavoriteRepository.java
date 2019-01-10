package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.Actions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerFavoriteRepository extends MongoRepository<Actions.AnswerFavorite, String> {
    int countByUserAndTarget(String user, String answer);

    long countByTarget(String answer);

    int deleteByUserAndTarget(String user, String answer);

    Page<Actions.AnswerFavorite> findByUser(String user, Pageable pageable);
}
