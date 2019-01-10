package com.askdog.store.model.repository.mongo;


import com.askdog.store.model.data.Area;
import com.askdog.store.model.repository.extend.ExtendedAreaTreeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AreaTreeRepository extends MongoRepository<Area, String>, ExtendedAreaTreeRepository {
}
