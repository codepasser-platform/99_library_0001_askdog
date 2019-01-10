package com.askdog.model.data;

import com.askdog.model.data.inner.ResourceDescription;
import com.askdog.model.data.inner.ResourceType;
import com.askdog.model.data.inner.StorageProvider;

import java.util.Date;

public class StorageRecord extends Base {

    private String linkId;
    private StorageProvider provider;
    private Date creationDate;
    private ResourceType resourceType;
    private ResourceDescription description;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public StorageProvider getProvider() {
        return provider;
    }

    public void setProvider(StorageProvider provider) {
        this.provider = provider;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceDescription getDescription() {
        return description;
    }

    public void setDescription(ResourceDescription description) {
        this.description = description;
    }
}
