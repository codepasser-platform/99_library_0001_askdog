package com.askdog.model.entity.builder;

import com.askdog.model.entity.StorageLink;
import com.askdog.model.entity.StorageLink.Status;

public class StorageLinkBuilder {

    private Status state;

    private StorageLinkBuilder() {
    }

    public StorageLinkBuilder state(StorageLink.Status state) {
        this.state = state;
        return this;
    }

    public StorageLink build() {
        StorageLink storageLink = new StorageLink();
        storageLink.setStatus(state);
        return storageLink;
    }

    public static StorageLinkBuilder storageLinkBuilder() {
        return new StorageLinkBuilder();
    }

}
