package com.askdog.store.service;

import com.askdog.store.service.bo.GoodsDetail;
import com.askdog.store.service.exception.ServiceException;

import javax.annotation.Nonnull;

public interface GoodsService {

    @Nonnull
    GoodsDetail find(@Nonnull String uuid) throws ServiceException;
}
