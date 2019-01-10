package com.askdog.store.model.data.builder;

import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.inner.TargetType;

import javax.validation.constraints.NotNull;
import java.util.Date;

public final class ResourceRecordBuilder {

    private String target;

    private TargetType targetType;

    private String persistenceName;

    public ResourceRecordBuilder setTarget(String target) {
        this.target = target;
        return this;
    }

    public ResourceRecordBuilder setTargetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public ResourceRecordBuilder setPersistenceName(String persistenceName) {
        this.persistenceName = persistenceName;
        return this;
    }

    public ResourceRecord build() {
        ResourceRecord record = new ResourceRecord();
        record.setTarget(this.target);
        record.setTargetType(this.targetType);
        record.setPersistenceName(this.persistenceName);
        record.setCreationDate(new Date());
        return record;
    }

    public static ResourceRecordBuilder resourceRecordBuilder() {
        return new ResourceRecordBuilder();
    }

}
