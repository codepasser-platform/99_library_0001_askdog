package com.askdog.store.model.repository;

import com.askdog.store.model.entity.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, String> {
}
