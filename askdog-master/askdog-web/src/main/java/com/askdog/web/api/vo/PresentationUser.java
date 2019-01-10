package com.askdog.web.api.vo;

import com.askdog.common.Out;
import com.askdog.model.data.UserAttribute.Gender;
import com.askdog.service.bo.UserDetail;
import org.springframework.util.Assert;

public class PresentationUser implements Out<PresentationUser, UserDetail> {

    private String id;
    private String name;
    private String avatar;
    private Gender gender;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public PresentationUser from(UserDetail user) {
        Assert.notNull(user, "UserDetail cannot be null");
        setId(user.getUuid());
        setName(user.getName());
        setAvatar(user.getAvatar());
        setGender(user.getGender());
        return this;
    }
}
