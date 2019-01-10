package com.askdog.store.service;


import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.inner.area.AreaTree.AreaLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface LocationService {

    @Nonnull
    <T extends Area> List<T> areas(AreaLevel level, @Nullable String parentId);
}
