package com.askdog.store.model.repository.mongo;

import com.askdog.store.model.data.Delivery;
import com.askdog.store.model.data.Sku;
import com.askdog.store.model.data.inner.TargetType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    List<Delivery> findByTargetAndTargetType(String target, TargetType targetType);

    Optional<Delivery> findByIdAndTarget(String id, String target);
}
