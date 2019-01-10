package com.askdog.model.repository.mongo.snapshot;

import com.askdog.model.data.snapshot.UserSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSnapshotRepository extends MongoRepository<UserSnapshot, String> {
}
