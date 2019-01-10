package com.askdog.model.repository.mongo;

import com.askdog.model.data.IncentiveLog;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IncentiveLogRepository extends MongoRepository<IncentiveLog, String> {
    Optional<IncentiveLog> findById(String id);

    Page<IncentiveLog> findByIncentiveOwnerIdAndIncentiveType(String incentiveOwnerId, IncentiveType incentiveType, Pageable pageable);

    Optional<IncentiveLog> findByIncentiveOwnerIdAndIncentiveTypeAndIncentiveReason(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason);

    Optional<IncentiveLog> findTopByIncentiveOwnerIdAndIncentiveTypeAndIncentiveReasonOrderByCreateTimeDesc(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason);
}
