package com.askdog.model.repository.mongo;

import com.askdog.model.data.ShareLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShareLogRepository extends MongoRepository<ShareLog, String> {

    Optional<ShareLog> findByUserIdAndTargetId(String userId, String targetId);
}
