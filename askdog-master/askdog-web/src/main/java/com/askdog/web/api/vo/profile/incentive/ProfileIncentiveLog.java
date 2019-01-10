package com.askdog.web.api.vo.profile.incentive;

import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import com.askdog.web.api.vo.common.event.EventVo;

import java.util.Date;
import java.util.Map;

public class ProfileIncentiveLog {

    private EventVo event;
    private IncentiveReason incentiveReason;
    private IncentiveType incentiveType;
    private long incentiveValue;
    private Map<String, Object> incentiveMetadata;
    private Date createTime;

    public EventVo getEvent() {
        return event;
    }

    public void setEvent(EventVo event) {
        this.event = event;
    }

    public IncentiveReason getIncentiveReason() {
        return incentiveReason;
    }

    public void setIncentiveReason(IncentiveReason incentiveReason) {
        this.incentiveReason = incentiveReason;
    }

    public IncentiveType getIncentiveType() {
        return incentiveType;
    }

    public void setIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
    }

    public long getIncentiveValue() {
        return incentiveValue;
    }

    public void setIncentiveValue(long incentiveValue) {
        this.incentiveValue = incentiveValue;
    }

    public Map<String, Object> getIncentiveMetadata() {
        return incentiveMetadata;
    }

    public void setIncentiveMetadata(Map<String, Object> incentiveMetadata) {
        this.incentiveMetadata = incentiveMetadata;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
