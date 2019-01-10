package com.askdog.store.web.api.vo;

import com.askdog.common.In;
import com.askdog.store.service.bo.PureDelivery;

public class PostedDelivery implements In<PureDelivery> {

    private String recipient;

    private String phoneNumber;

    private String provinceId;

    private String province;

    private String cityId;

    private String city;

    private String countyId;

    private String county;

    private String address;

    private String label;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public PureDelivery convert() {
        PureDelivery pureDelivery = new PureDelivery();
        pureDelivery.setRecipient(this.recipient);
        pureDelivery.setPhoneNumber(this.phoneNumber);
        pureDelivery.setProvinceId(this.provinceId);
        pureDelivery.setProvince(this.province);
        pureDelivery.setCityId(this.cityId);
        pureDelivery.setCity(this.city);
        pureDelivery.setCountyId(this.countyId);
        pureDelivery.setCounty(this.county);
        pureDelivery.setAddress(this.address);
        pureDelivery.setLabel(this.label);
        return pureDelivery;
    }
}
