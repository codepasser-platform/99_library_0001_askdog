package com.askdog.store.model.repository;

import com.askdog.store.model.entity.Buyer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends CrudRepository<Buyer, String> {

    Optional<Buyer> findByName(String name);

    Optional<Buyer> findByBuyerId(String buyerId);
}
