package com.askdog.service.storage;

public class StorageResource {

    private String linkId;
    private String url;

    public StorageResource(String linkId, String url) {
        this.linkId = linkId;
        this.url = url;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
