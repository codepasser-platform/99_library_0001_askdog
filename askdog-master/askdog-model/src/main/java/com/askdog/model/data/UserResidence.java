package com.askdog.model.data;

import javax.annotation.Nonnull;
import java.util.Date;

public class UserResidence extends UserLocation {

    private double rate;

    private Date loginAt;

    @Nonnull
    public Double getLng() {
        return this.getGeometry().getCoordinates()[0];
    }

    @Nonnull
    public Double getLat() {
        return this.getGeometry().getCoordinates()[1];
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }
}
