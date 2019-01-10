package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.inner.VoteDirection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface VoteRepository<V> extends MongoRepository<V, String> {
    long countByTarget(String target);
    long countByTargetAndDirection(String target, VoteDirection direction);
    Optional<V> findByUserAndTarget(String userId, String target);
    long deleteByUserAndTarget(String userId, String target);
}
