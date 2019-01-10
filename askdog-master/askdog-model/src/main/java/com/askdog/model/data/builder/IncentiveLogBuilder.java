package com.askdog.model.data.builder;

import com.askdog.model.data.IncentiveLog;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;

import java.util.Date;
import java.util.Map;

public class IncentiveLogBuilder {

    private String eventLogId;
    private String incentiveOwnerId;
    private IncentiveReason incentiveReason;
    private IncentiveType incentiveType;
    private long incentiveValue;
    private Map<String, Object> incentiveMetadata;
    private Date createTime = new Date();

    public static IncentiveLogBuilder getBuilder() {
        return new IncentiveLogBuilder();
    }

    public IncentiveLogBuilder setEventLogId(String eventLogId) {
        this.eventLogId = eventLogId;
        return this;
    }

    public IncentiveLogBuilder setIncentiveOwnerId(String incentiveOwnerId) {
        this.incentiveOwnerId = incentiveOwnerId;
        return this;
    }

    public IncentiveLogBuilder setIncentiveReason(IncentiveReason incentiveReason) {
        this.incentiveReason = incentiveReason;
        return this;
    }

    public IncentiveLogBuilder setIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
        return this;
    }

    public IncentiveLogBuilder setIncentiveValue(int incentiveValue) {
        this.incentiveValue = incentiveValue;
        return this;
    }

    public IncentiveLogBuilder setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public IncentiveLogBuilder setIncentiveMetadata(Map<String, Object> incentiveMetadata) {
        this.incentiveMetadata = incentiveMetadata;
        return this;
    }

    public IncentiveLog build() {
        IncentiveLog incentiveLog = new IncentiveLog();
        incentiveLog.setEventLogId(eventLogId);
        incentiveLog.setIncentiveOwnerId(incentiveOwnerId);
        incentiveLog.setIncentiveReason(incentiveReason);
        incentiveLog.setIncentiveType(incentiveType);
        incentiveLog.setIncentiveValue(incentiveValue);
        incentiveLog.setIncentiveMetadata(incentiveMetadata);
        incentiveLog.setCreateTime(createTime);
        return incentiveLog;
    }
}
