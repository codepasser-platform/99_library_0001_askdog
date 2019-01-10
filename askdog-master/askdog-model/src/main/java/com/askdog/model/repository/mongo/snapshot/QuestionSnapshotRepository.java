package com.askdog.model.repository.mongo.snapshot;

import com.askdog.model.data.snapshot.QuestionSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QuestionSnapshotRepository extends MongoRepository<QuestionSnapshot, String> {
    Optional<QuestionSnapshot> findByQuestionId(String questionId);
}
