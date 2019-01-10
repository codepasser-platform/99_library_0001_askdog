package com.askdog.store.service.bo;

import com.askdog.common.Out;
import com.askdog.store.model.data.Delivery;
import com.askdog.store.model.data.inner.TargetType;

public class DeliveryDetail implements Out<DeliveryDetail, Delivery> {

    private String id;

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

    private String target;

    private TargetType targetType;

    private boolean isDefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public DeliveryDetail from(Delivery delivery) {
        this.id = delivery.getId();
        this.recipient = delivery.getRecipient();
        this.phoneNumber = delivery.getPhoneNumber();
        this.provinceId = delivery.getProvinceId();
        this.province = delivery.getProvince();
        this.cityId = delivery.getCityId();
        this.city = delivery.getCity();
        this.countyId = delivery.getCountyId();
        this.county = delivery.getCounty();
        this.address = delivery.getAddress();
        this.label = delivery.getLabel();
        this.isDefault = delivery.isDefault();
        this.target = delivery.getTarget();
        this.targetType = delivery.getTargetType();
        return this;
    }
}
