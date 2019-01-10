package com.askdog.web.api.vo;

import com.askdog.common.In;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.user.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.askdog.common.RegexPattern.REGEX_MAIL;
import static com.askdog.common.RegexPattern.REGEX_USER_NAME;

public class RegisterUser implements In<User> {

    @NotNull
    @Pattern(regexp = REGEX_USER_NAME)
    private String name;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @Pattern(regexp = REGEX_MAIL)
    private String mail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public User convert() {
        User user = new User();
        user.setName(this.name);
        user.setPassword(this.password);
        user.setEmail(this.mail);
        user.setType(UserType.REGISTERED);
        return user;
    }
}
