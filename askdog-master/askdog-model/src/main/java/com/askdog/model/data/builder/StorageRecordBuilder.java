package com.askdog.model.data.builder;

import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.ResourceDescription;
import com.askdog.model.data.inner.ResourceType;
import com.askdog.model.data.inner.StorageProvider;

import java.util.Date;

public final class StorageRecordBuilder {

    private String linkId;
    private StorageProvider provider;
    private ResourceType resourceType;
    private ResourceDescription description;

    public StorageRecordBuilder linkId(String linkId) {
        this.linkId = linkId;
        return this;
    }

    public StorageRecordBuilder provider(StorageProvider provider) {
        this.provider = provider;
        return this;
    }

    public StorageRecordBuilder resourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public StorageRecordBuilder description(ResourceDescription description) {
        this.description = description;
        return this;
    }

    public StorageRecord build() {
        StorageRecord record = new StorageRecord();
        record.setLinkId(this.linkId);
        record.setProvider(this.provider);
        record.setResourceType(this.resourceType);
        record.setDescription(this.description);
        record.setCreationDate(new Date());
        return record;
    }

    public static StorageRecordBuilder storageRecordBuilder() {
        return new StorageRecordBuilder();
    }

}
