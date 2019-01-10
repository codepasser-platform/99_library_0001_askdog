package com.askdog.store.model.repository.mongo;

import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.inner.TargetType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRecordRepository extends MongoRepository<ResourceRecord, String> {
    List<ResourceRecord> findByTargetAndTargetType(String target, TargetType targetType);
}
