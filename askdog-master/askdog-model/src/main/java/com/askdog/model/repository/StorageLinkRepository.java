package com.askdog.model.repository;

import com.askdog.model.entity.StorageLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface StorageLinkRepository extends CrudRepository<StorageLink, String> {
    Optional<StorageLink> findByUuid(String id);

    @Modifying
    @Query("UPDATE StorageLink r SET r.status = :status WHERE r.uuid IN :uuids")
    int updateState(@Param("uuids") Set<String> uuids, @Param("status") StorageLink.Status state);

}
