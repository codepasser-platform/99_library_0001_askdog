package com.askdog.store.model.data.builder;

import com.askdog.store.model.data.Delivery;
import com.askdog.store.model.data.inner.TargetType;

public final class DeliveryBuilder {

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

    public DeliveryBuilder setRecipient(String recipient) {
        this.recipient = recipient;
        return this;
    }

    public DeliveryBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public DeliveryBuilder setProvinceId(String provinceId) {
        this.provinceId = provinceId;
        return this;
    }


    public DeliveryBuilder setProvince(String province) {
        this.province = province;
        return this;
    }

    public DeliveryBuilder setCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }


    public DeliveryBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public DeliveryBuilder setCountyId(String countyId) {
        this.countyId = countyId;
        return this;
    }

    public DeliveryBuilder setCounty(String county) {
        this.county = county;
        return this;
    }

    public DeliveryBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public DeliveryBuilder setLabel(String label) {
        this.label = label;
        return this;
    }

    public DeliveryBuilder setTarget(String target) {
        this.target = target;
        return this;
    }

    public DeliveryBuilder setTargetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public DeliveryBuilder setDefault(boolean aDefault) {
        isDefault = aDefault;
        return this;
    }

    public Delivery build() {
        Delivery delivery = new Delivery();
        delivery.setRecipient(this.recipient);
        delivery.setPhoneNumber(this.phoneNumber);
        delivery.setProvinceId(this.provinceId);
        delivery.setProvince(this.province);
        delivery.setCityId(this.cityId);
        delivery.setCity(this.city);
        delivery.setCountyId(this.countyId);
        delivery.setCounty(this.county);
        delivery.setAddress(this.address);
        delivery.setLabel(this.label);
        delivery.setTarget(this.target);
        delivery.setTargetType(this.targetType);
        delivery.setDefault(this.isDefault);
        return delivery;
    }

    public static DeliveryBuilder deliveryBuilder() {
        return new DeliveryBuilder();
    }
}
