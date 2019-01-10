package com.askdog.model.repository.mongo;

import com.askdog.model.data.EventLog;
import com.askdog.model.repository.extend.ExtendedEventLogRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventLogRepository extends MongoRepository<EventLog, String>, ExtendedEventLogRepository {
    Optional<EventLog> findById(String id);
}

