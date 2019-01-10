package com.askdog.model.repository;

import com.askdog.model.entity.ExternalUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalUserRepository extends CrudRepository<ExternalUser, String> {
    Optional<ExternalUser> findByExternalUserId(String externalUserId);
}