package com.askdog.model.repository.mongo;

import com.askdog.model.data.QuestionLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QuestionLocationRepository extends MongoRepository<QuestionLocation, String> {
    Optional<QuestionLocation> findByTarget(String target);
}
