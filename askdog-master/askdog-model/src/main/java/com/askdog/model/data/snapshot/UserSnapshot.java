package com.askdog.model.data.snapshot;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ad_snapshot_user")
public class UserSnapshot extends Snapshot<UserSnapshot> {

    private String userId;

    private String name;

    private String avatarUrl;

    public String getUserId() {
        return userId;
    }

    public UserSnapshot setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserSnapshot setName(String name) {
        this.name = name;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UserSnapshot setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }
}
