package com.askdog.web.test.json;

import com.askdog.web.api.vo.RegisterUser;

public class RegisterUserBuilder {

    private String name;
    private String password;
    private String mail;

    public RegisterUserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RegisterUserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public RegisterUserBuilder mail(String mail) {
        this.mail = mail;
        return this;
    }

    public RegisterUser build() {
        RegisterUser user = new RegisterUser();
        user.setName(this.name);
        user.setMail(this.mail);
        user.setPassword(this.password);
        return user;
    }

    public static RegisterUserBuilder registerUserBuilder() {
        return new RegisterUserBuilder();
    }
}
