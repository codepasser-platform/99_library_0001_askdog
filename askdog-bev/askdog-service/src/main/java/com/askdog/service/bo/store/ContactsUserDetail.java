package com.askdog.service.bo.store;

import java.io.Serializable;

public class ContactsUserDetail implements Serializable {

    private static final long serialVersionUID = 987938879358694775L;
    private String name;
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
