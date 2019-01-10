package com.askdog.web.api.vo;

import com.askdog.model.validation.UserIdentifier;
import com.askdog.common.RegexPattern;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@UserIdentifier
public class AuthUser {

    @NotNull
    @Size(min = 3, max = 16)
    private String userName;

    @Email
    private String email;

    @Pattern(regexp = RegexPattern.REGEX_PHONE)
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = ".+")
    private String plainPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
}
