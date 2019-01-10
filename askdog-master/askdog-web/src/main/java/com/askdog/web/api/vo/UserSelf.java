package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.user.UserType;
import com.askdog.service.bo.UserDetail;

public class UserSelf implements Out<UserSelf, UserDetail> {

    private String id;
    private String name;
    private String mail;
    private UserType type;
    private String avatar;
    private long points;
    private long exp;
    private long noticeCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(long noticeCount) {
        this.noticeCount = noticeCount;
    }

    @Override
    public UserSelf from(UserDetail user) {
        this.id = user.getUuid();
        this.name = user.getName();
        this.mail = user.getMail();
        this.type = user.getType();
        this.avatar = user.getAvatar();
        this.points = user.getPoints();
        this.exp = user.getExp();
        return this;
    }
}
