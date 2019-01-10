package com.askdog.store.web.api.vo;

import com.askdog.common.Out;
import com.askdog.store.service.bo.BuyerDetail;

public class BuyerSelf implements Out<BuyerSelf, BuyerDetail> {

    private String name;
    private String mail;
    private String avatar;
    private String nickName;
    private String phoneNumber;
    private long points;

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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    @Override
    public BuyerSelf from(BuyerDetail detail) {
        this.name = detail.getName();
        this.mail = detail.getMail();
        this.avatar = detail.getAvatar();
        this.nickName = detail.getNickName();
        this.phoneNumber = detail.getPhoneNumber();
        this.points = detail.getPoints();
        return this;
    }
}
