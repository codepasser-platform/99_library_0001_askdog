package com.stock.capital.ipo.dao.repository.mongo;

import com.stock.capital.ipo.model.data.UserAttribute;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAttributeRepository extends MongoRepository<UserAttribute, String> {

  Optional<UserAttribute> findByUserId(Long userId);
}
