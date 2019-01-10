package com.askdog.web.api.helper;

import com.askdog.model.data.IncentiveLog;
import com.askdog.service.IncentiveLogService;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.api.vo.profile.incentive.ProfileIncentiveLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncentiveHelper {

    @Autowired private EventHelper eventHelper;
    @Autowired private IncentiveLogService incentiveLogService;

    public ProfileIncentiveLog convert(String incentiveLogId) throws ServiceException {
        IncentiveLog incentiveLog = incentiveLogService.findById(incentiveLogId);
        ProfileIncentiveLog profileIncentiveLog = new ProfileIncentiveLog();
        profileIncentiveLog.setIncentiveReason(incentiveLog.getIncentiveReason());
        profileIncentiveLog.setIncentiveValue(incentiveLog.getIncentiveValue());
        profileIncentiveLog.setIncentiveType(incentiveLog.getIncentiveType());
        profileIncentiveLog.setIncentiveMetadata(incentiveLog.getIncentiveMetadata());
        profileIncentiveLog.setCreateTime(incentiveLog.getCreateTime());
        profileIncentiveLog.setEvent(eventHelper.convert(incentiveLog.getEventLogId()));
        return profileIncentiveLog;
    }
}
