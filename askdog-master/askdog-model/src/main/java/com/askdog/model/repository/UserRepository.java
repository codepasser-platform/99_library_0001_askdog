package com.askdog.model.repository;

import com.askdog.model.entity.StorageLink;
import com.askdog.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUuid(String uuid);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.name = ?1")
    boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.email = ?1")
    boolean existsByEmail(String mail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.points = u.points + ?2 WHERE u.id = ?1")
    int updatePoints(String userId, int value);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.exp = u.exp + ?2 WHERE u.id = ?1")
    int updateExp(String userId, int value);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.phoneNumber = ?2 WHERE u.id = ?1")
    int updatePhone(String userId, String value);

}