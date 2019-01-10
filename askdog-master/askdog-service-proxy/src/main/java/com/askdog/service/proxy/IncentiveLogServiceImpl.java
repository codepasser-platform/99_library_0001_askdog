package com.askdog.service.proxy;

import com.askdog.model.data.IncentiveLog;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import com.askdog.service.IncentiveLogService;
import com.askdog.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncentiveLogServiceImpl implements IncentiveLogService {

    @Override
    public IncentiveLog findById(String incentiveLogId) throws NotFoundException {
        return null;
    }

    @Override
    public Page<IncentiveLog> findIncentiveLog(String userId, IncentiveType incentiveType, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<IncentiveLog> findByUserAndIncentiveTypeAndIncentiveReson(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason) {
        return null;
    }

    @Override
    public Optional<IncentiveLog> findLastLoginLog(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason) {
        return null;
    }
}
