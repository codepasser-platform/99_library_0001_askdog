package com.askdog.service.impl.storage;

import com.askdog.dao.repository.mongo.StorageRecordRepository;
import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.ResourceDescription;
import com.askdog.model.data.inner.ResourceState;
import com.askdog.model.data.inner.ResourceType;
import com.askdog.model.data.inner.StorageProvider;
import com.askdog.service.exception.IllegalArgumentException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cache.annotation.video.ResourceCache;
import com.askdog.service.impl.cache.annotation.video.ResourceCacheRefresh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.askdog.common.utils.Conditions.checkState;
import static com.askdog.model.data.inner.ResourceState.PERSISTENT;
import static com.askdog.model.data.inner.ResourceState.TEMPORARY;
import static com.askdog.service.exception.IllegalArgumentException.Error.INVALID_RESOURCE;
import static com.askdog.service.exception.NotFoundException.Error.STORAGE_RECORD;
import static org.springframework.util.Assert.notNull;

@Component
public class StorageRecorder {

    @Autowired private StorageRecordRepository storageRecordRepository;

    public void assertValid(Long resourceId) {
        StorageRecord storageRecord = storageRecordRepository.findByResourceId(resourceId);
        checkState(storageRecord != null && storageRecord.getResourceState() == PERSISTENT,
                () -> new IllegalArgumentException(INVALID_RESOURCE));
    }

    public void assertVideoValid(Long resourceId) {
        StorageRecord storageRecord = storageRecordRepository.findByResourceId(resourceId);
        checkState(storageRecord != null, () -> new IllegalArgumentException(INVALID_RESOURCE));
    }

    public StorageRecord newRecord(StorageProvider provider, ResourceType resourceType, Long resourceId,
                                   ResourceDescription description) {
        StorageRecord record = new StorageRecord();
        record.setResourceId(resourceId);
        record.setProvider(provider);
        record.setResourceType(resourceType);
        record.setResourceState(TEMPORARY);
        record.setDescription(description);
        record.setCreationDate(new Date());
        return storageRecordRepository.save(record);
    }

    public StorageRecord updateResourceStatus(Long resourceId, ResourceState status) {
        StorageRecord storageRecord = getResource(resourceId);
        storageRecord.setResourceState(status);
        return storageRecordRepository.save(storageRecord);
    }

    @ResourceCache
    public StorageRecord getResource(Long resourceId) {
        notNull(resourceId);
        StorageRecord storageRecord = storageRecordRepository.findByResourceId(resourceId);
        checkState(storageRecord != null, () -> new NotFoundException(STORAGE_RECORD));
        return storageRecord;
    }

    @ResourceCacheRefresh
    public StorageRecord refreshResourceCahce(Long resourceId) {
        return getResource(resourceId);
    }

    public StorageRecord save(StorageRecord storageRecord) {
        return storageRecordRepository.save(storageRecord);
    }

}
