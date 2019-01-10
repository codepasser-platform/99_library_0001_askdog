package com.askdog.service;

import com.askdog.model.data.IncentiveLog;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import com.askdog.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IncentiveLogService {
    IncentiveLog findById(String incentiveLogId) throws NotFoundException;
    Page<IncentiveLog> findIncentiveLog(String userId, IncentiveType incentiveType, Pageable pageable);
    Optional<IncentiveLog> findByUserAndIncentiveTypeAndIncentiveReson(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason);
    Optional<IncentiveLog> findLastLoginLog(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason);
}
