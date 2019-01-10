package com.askdog.model.repository.mongo;

import com.askdog.model.data.UserResidence;
import com.askdog.model.repository.extend.ExtendedLocationRecordRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserResidenceRepository extends MongoRepository<UserResidence, String>, ExtendedLocationRecordRepository {
    Optional<UserResidence> findByTarget(String target);
}
