package com.askdog.service.bo.store;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

import static com.askdog.common.RegexPattern.REGEX_PHONE;

public class PureContactsUser {

    @NotEmpty
    private String name;
    @NotEmpty
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
