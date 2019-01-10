package com.askdog.model.repository.mongo;

import com.askdog.model.data.UserAttribute;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserAttributeRepository extends MongoRepository<UserAttribute, String> {
    Optional<UserAttribute> findByUserId(String userId);
}
