package com.askdog.service.impl.storage.aliyun;

import com.askdog.model.data.inner.ResourceDescription;

import javax.validation.constraints.NotNull;

public class OssResourceDescription extends ResourceDescription {

    @NotNull
    private String bucket;

    @NotNull
    private String fileName;

    @NotNull
    private String persistenceName;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPersistenceName() {
        return persistenceName;
    }

    public void setPersistenceName(String persistenceName) {
        this.persistenceName = persistenceName;
    }
}
