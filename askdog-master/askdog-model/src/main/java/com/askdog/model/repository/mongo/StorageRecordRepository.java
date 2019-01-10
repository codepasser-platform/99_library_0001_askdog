package com.askdog.model.repository.mongo;

import com.askdog.model.data.StorageRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StorageRecordRepository extends MongoRepository<StorageRecord, String> {
    Optional<StorageRecord> findByLinkId(String linkId);
}
