package com.askdog.model.data;

import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "ad_incentive_record")
public class IncentiveLog extends Base {

    private String eventLogId;
    private String incentiveOwnerId;
    private IncentiveReason incentiveReason;
    private IncentiveType incentiveType;
    private long incentiveValue;
    private Map<String, Object> incentiveMetadata;
    private Date createTime;

    public String getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(String eventLogId) {
        this.eventLogId = eventLogId;
    }

    public String getIncentiveOwnerId() {
        return incentiveOwnerId;
    }

    public void setIncentiveOwnerId(String incentiveOwnerId) {
        this.incentiveOwnerId = incentiveOwnerId;
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
