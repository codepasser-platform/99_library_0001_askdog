package com.askdog.store.model.data.builder;

import com.askdog.store.model.data.Banner;
import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.inner.TargetType;

import javax.validation.constraints.NotNull;
import java.util.Date;

public final class BannerBuilder {

    private String target;

    private TargetType targetType;

    public BannerBuilder setTarget(String target) {
        this.target = target;
        return this;
    }

    public BannerBuilder setTargetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public Banner build() {
        Banner banner = new Banner();
        banner.setTarget(this.target);
        banner.setTargetType(this.targetType);
        banner.setCreationDate(new Date());
        return banner;
    }

    public static BannerBuilder bannerBuilder() {
        return new BannerBuilder();
    }

}
