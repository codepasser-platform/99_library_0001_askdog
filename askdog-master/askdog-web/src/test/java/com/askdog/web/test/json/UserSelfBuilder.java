package com.askdog.web.test.json;

import com.askdog.model.entity.inner.user.UserType;
import com.askdog.web.api.vo.UserSelf;

public class UserSelfBuilder {

    private String name;
    private String mail;
    private UserType type;

    public UserSelfBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserSelfBuilder mail(String email) {
        this.mail = email;
        return this;
    }

    public UserSelfBuilder type(UserType type) {
        this.type = type;
        return this;
    }

    public UserSelf build() {
        UserSelf self = new UserSelf();
        self.setName(this.name);
        self.setMail(this.mail);
        self.setType(this.type);
        return self;
    }

    public static UserSelfBuilder userSelfBuilder() {
        return new UserSelfBuilder();
    }
}
