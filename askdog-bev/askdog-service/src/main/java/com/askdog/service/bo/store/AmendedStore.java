package com.askdog.service.bo.store;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.askdog.common.RegexPattern.REGEX_PHONE;

public class AmendedStore {

    @NotEmpty
    @Size(min = 1, max = 24)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 200)
    private String description;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String address;

    @NotEmpty
    @Pattern(regexp = REGEX_PHONE)
    private String phone;

    private Long coverImageLinkId;

    private PureContactsUser pureContactsUser;

    private PureStore.Location location;

    private String type;

    private Float cpc;

    private String businessHours;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCoverImageLinkId() {
        return coverImageLinkId;
    }

    public void setCoverImageLinkId(Long coverImageLinkId) {
        this.coverImageLinkId = coverImageLinkId;
    }

    public PureContactsUser getPureContactsUser() {
        return pureContactsUser;
    }

    public void setPureContactsUser(PureContactsUser pureContactsUser) {
        this.pureContactsUser = pureContactsUser;
    }

    public PureStore.Location getLocation() {
        return location;
    }

    public void setLocation(PureStore.Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getCpc() {
        return cpc;
    }

    public void setCpc(Float cpc) {
        this.cpc = cpc;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }
}
