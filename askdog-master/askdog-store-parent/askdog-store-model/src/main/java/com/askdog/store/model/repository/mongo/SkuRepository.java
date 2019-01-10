package com.askdog.store.model.repository.mongo;

import com.askdog.store.model.data.Sku;
import com.askdog.store.model.data.inner.TargetType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SkuRepository extends MongoRepository<Sku, String> {
    Optional<Sku> findByTargetAndTargetType(String target, TargetType targetType);
}
