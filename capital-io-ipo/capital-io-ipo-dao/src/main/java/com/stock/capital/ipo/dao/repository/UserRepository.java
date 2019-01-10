package com.stock.capital.ipo.dao.repository;

import com.stock.capital.common.model.entity.inner.State;
import com.stock.capital.ipo.model.entity.User;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * [UserRepository].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

  Optional<User> findById(long id);

  Optional<User> findByUsername(String username);

  Optional<User> findByPhoneNumber(String phoneNumber);

  Optional<User> findByEmail(String email);

  long deleteByUsername(String username);

  Page<User> findAllByStateNotIn(Set<State> states, Pageable pageable);
}
