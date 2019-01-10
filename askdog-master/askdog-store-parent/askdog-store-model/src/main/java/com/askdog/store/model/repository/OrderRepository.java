package com.askdog.store.model.repository;

import com.askdog.store.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

    Optional<Order> findByUuid(String id);

    Page<Order> findByOwner_Uuid(String buyerId, Pageable paging);

    Optional<Order> findByOwner_UuidAndUuid(String buyerId, String id);
}
