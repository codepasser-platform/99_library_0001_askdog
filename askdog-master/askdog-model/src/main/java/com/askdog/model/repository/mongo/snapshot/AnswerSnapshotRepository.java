package com.askdog.model.repository.mongo.snapshot;

import com.askdog.model.data.snapshot.AnswerSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AnswerSnapshotRepository extends MongoRepository<AnswerSnapshot, String> {

    Optional<AnswerSnapshot> findByAnswerId(String answerId);
}
