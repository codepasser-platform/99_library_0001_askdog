package com.askdog.store.model.data.builder;

import com.askdog.store.model.data.Sku;
import com.askdog.store.model.data.Sku.SkuItem;
import com.askdog.store.model.data.inner.TargetType;

import java.util.List;

public final class SkuBuilder {

    private String target;

    private TargetType targetType;

    private List<SkuItem> skuItems;

    public SkuBuilder setTarget(String target) {
        this.target = target;
        return this;
    }

    public SkuBuilder setTargetType(TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public SkuBuilder setSkuItems(List<SkuItem> skuItems) {
        this.skuItems = skuItems;
        return this;
    }

    public Sku build() {
        Sku sku = new Sku();
        sku.setTarget(this.target);
        sku.setTargetType(this.targetType);
        sku.setSkuItems(this.skuItems);
        return sku;
    }

    public static SkuBuilder skuBuilder() {
        return new SkuBuilder();
    }

}
