package com.askdog.service.impl;

import com.askdog.model.data.IncentiveLog;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import com.askdog.model.repository.mongo.IncentiveLogRepository;
import com.askdog.service.IncentiveLogService;
import com.askdog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.askdog.service.exception.NotFoundException.Error.INCENTIVE_LOG;

@Service
public class IncentiveLogServiceImpl implements IncentiveLogService {

    @Autowired
    private IncentiveLogRepository incentiveLogRepository;

    @Override public IncentiveLog findById(String incentiveLogId) throws NotFoundException {
        return incentiveLogRepository.findById(incentiveLogId).orElseThrow(() -> new NotFoundException(INCENTIVE_LOG));
    }

    @Override
    public Page<IncentiveLog> findIncentiveLog(String userId, IncentiveType incentiveType, Pageable pageable) {
        return incentiveLogRepository.findByIncentiveOwnerIdAndIncentiveType(userId, incentiveType, pageable);
    }

    @Override
    public Optional<IncentiveLog> findByUserAndIncentiveTypeAndIncentiveReson(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason) {
        return incentiveLogRepository.findByIncentiveOwnerIdAndIncentiveTypeAndIncentiveReason(userId, incentiveType, incentiveReason);
    }

    @Override
    public Optional<IncentiveLog> findLastLoginLog(String userId, IncentiveType incentiveType, IncentiveReason incentiveReason) {
        // TODO
        return incentiveLogRepository.findTopByIncentiveOwnerIdAndIncentiveTypeAndIncentiveReasonOrderByCreateTimeDesc(userId, incentiveType, incentiveReason);
    }
}
