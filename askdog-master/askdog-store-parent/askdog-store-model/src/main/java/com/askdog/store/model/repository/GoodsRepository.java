package com.askdog.store.model.repository;

import com.askdog.store.model.entity.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, String> {
    Optional<Goods> findByUuid(String uuid);
}
