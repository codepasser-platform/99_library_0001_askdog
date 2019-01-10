package com.askdog.store.model.entity.builder;

import com.askdog.store.model.entity.Store;

import javax.validation.constraints.NotNull;
import java.util.Date;

public final class StoreBuilder {

    private String name;

    public StoreBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Store build() {
        Store store = new Store();
        store.setName(this.name);
        store.setCreationTime(new Date());
        return store;
    }

    public static StoreBuilder storeBuilder() {
        return new StoreBuilder();
    }
}
