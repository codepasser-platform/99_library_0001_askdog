package com.askdog.dao.repository.mongo;

import com.askdog.model.common.State;
import com.askdog.model.data.StoreAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface StoreAttributeRepository extends MongoRepository<StoreAttribute, String> {
    StoreAttribute findByStoreId(Long storeId);

    Page<StoreAttribute> findByGeoNearAndStateNotIn(Point point, Distance max, Set<State> states, Pageable pageable);
}
