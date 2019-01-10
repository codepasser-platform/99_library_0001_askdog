package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.user.UserType;

public class UserInfo implements Out<UserInfo, User> {

    private String uuid;
    private String name;
    private String mail;
    private UserType type;
    private String avatar;
    private String nickName;
    private String phoneNumber;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public UserInfo from(User entity) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUuid(entity.getUuid());
        userInfo.setName(entity.getName());
        userInfo.setMail(entity.getEmail());
        userInfo.setType(entity.getType());
        userInfo.setAvatar(entity.getAvatarUrl());
        userInfo.setNickName(entity.getNickName());
        userInfo.setPhoneNumber(entity.getPhoneNumber());
        return userInfo;
    }
}
