package com.askdog.service.impl.storage;

import com.askdog.model.data.StorageRecord;
import com.askdog.model.data.inner.ResourceDescription;
import com.askdog.model.data.inner.ResourceType;
import com.askdog.model.data.inner.StorageProvider;
import com.askdog.model.entity.StorageLink;
import com.askdog.model.entity.StorageLink.Status;
import com.askdog.model.repository.StorageLinkRepository;
import com.askdog.model.repository.mongo.StorageRecordRepository;
import com.askdog.service.exception.ConflictException;
import com.askdog.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.askdog.model.data.builder.StorageRecordBuilder.storageRecordBuilder;
import static com.askdog.model.entity.StorageLink.Status.TEMPORARY;
import static com.askdog.model.entity.builder.StorageLinkBuilder.storageLinkBuilder;
import static com.askdog.service.exception.NotFoundException.Error.STORAGE_LINK;
import static com.askdog.service.exception.NotFoundException.Error.STORAGE_RECORD;

@Component
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class StorageRecorder {

    @Autowired
    private StorageLinkRepository storageLinkRepository;

    @Autowired
    private StorageRecordRepository storageRecordRepository;

    public StorageLink checkExist(String linkId) throws NotFoundException {
        return storageLinkRepository.findByUuid(linkId).orElseThrow(() -> new NotFoundException(STORAGE_LINK));
    }

    public StorageRecord newRecord(StorageProvider provider, ResourceType resourceType, ResourceDescription description) throws ConflictException {
        StorageLink link = storageLinkRepository.save(storageLinkBuilder().state(TEMPORARY).build());
        StorageRecord storageRecord = storageRecordBuilder()
                .linkId(link.getUuid())
                .provider(provider)
                .resourceType(resourceType)
                .description(description)
                .build();
        return storageRecordRepository.save(storageRecord);
    }

    public StorageRecord updateLinkStatus(String linkId, Status status) throws NotFoundException {
        StorageLink storageLink = storageLinkRepository.findByUuid(linkId).orElseThrow(() -> new NotFoundException(STORAGE_LINK));
        if (!storageLink.getStatus().equals(status)) {
            storageLink.setStatus(status);
            storageLinkRepository.save(storageLink);
        }

        return storageRecordRepository.findByLinkId(linkId).orElseThrow(() -> new NotFoundException(STORAGE_RECORD));
    }

    public StorageRecord findStorageRecord(String linkId) throws NotFoundException {
        return storageRecordRepository.findByLinkId(linkId).orElseThrow(() -> new NotFoundException(STORAGE_RECORD));
    }

}
