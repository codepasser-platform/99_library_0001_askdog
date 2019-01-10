package com.askdog.model.data.builder;

import com.askdog.model.data.QuestionLocation;
import com.askdog.model.data.UserLocation;
import com.askdog.model.data.UserResidence;
import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.inner.location.LocationDescription;
import com.askdog.model.data.inner.location.LocationRecord;
import com.askdog.model.data.inner.location.LocationProvider;

import javax.validation.constraints.NotNull;
import java.util.Date;

public final class LocationRecordBuilder {


    private LocationProvider provider;
    @NotNull
    private TargetType targetType;
    @NotNull
    private String target;
    private String ip;
    private LocationDescription description;
    private Date creationDate;

    public String getTarget() {
        return target;
    }

    public LocationRecordBuilder setTarget(String target) {
        this.target = target;
        return this;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public LocationRecordBuilder setTargetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public LocationRecordBuilder setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public LocationRecordBuilder setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public LocationProvider getProvider() {
        return provider;
    }

    public LocationRecordBuilder setProvider(LocationProvider provider) {
        this.provider = provider;
        return this;
    }

    public LocationDescription getDescription() {
        return description;
    }

    public LocationRecordBuilder setDescription(LocationDescription description) {
        this.description = description;
        return this;
    }


    public LocationRecord build() {
        LocationRecord record = new LocationRecord();
        record.setProvider(this.provider);
        record.setTargetType(this.targetType);
        record.setTarget(this.target);
        record.setIp(this.ip);
        record.setDescription(this.description);
        record.setCreationDate(new Date());
        record.getGeometry();
        return record;
    }

    public UserLocation buildUserLocation() {
        UserLocation record = new UserLocation();
        record.setProvider(this.provider);
        record.setTargetType(this.targetType);
        record.setTarget(this.target);
        record.setIp(this.ip);
        record.setDescription(this.description);
        record.setCreationDate(new Date());
        record.getGeometry();
        return record;
    }

    public UserResidence bulidUserResidence(double rate, @NotNull Date loginAt) {
        UserResidence record = new UserResidence();
        record.setProvider(this.provider);
        record.setTargetType(this.targetType);
        record.setTarget(this.target);
        record.setIp(this.ip);
        record.setDescription(this.description);
        record.setCreationDate(new Date());
        record.setRate(rate);
        record.setLoginAt(loginAt);
        record.getGeometry();
        return record;
    }

    public QuestionLocation buildQuestionLocation() {
        QuestionLocation record = new QuestionLocation();
        record.setProvider(this.provider);
        record.setTargetType(this.targetType);
        record.setTarget(this.target);
        record.setIp(this.ip);
        record.setDescription(this.description);
        record.setCreationDate(new Date());
        record.getGeometry();
        return record;
    }

    public static LocationRecordBuilder locationRecordBuilder() {
        return new LocationRecordBuilder();
    }

}
