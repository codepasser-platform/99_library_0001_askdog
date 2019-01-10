package com.askdog.service.impl.storage.aliyun;

import com.askdog.service.storage.AccessToken;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OssAccessToken implements AccessToken {

    @JsonProperty("OSSAccessKeyId")
    private String accessId;
    private String policy;
    private String signature;
    @JsonProperty("key")
    private String persistenceName;
    private String host;
    private long expire;
    private String callback;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPersistenceName() {
        return persistenceName;
    }

    public void setPersistenceName(String persistenceName) {
        this.persistenceName = persistenceName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

}
