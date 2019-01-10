package com.askdog.store.service;

import com.askdog.store.service.bo.BannerDetail;
import com.askdog.store.service.exception.ServiceException;

import javax.annotation.Nonnull;
import java.util.List;

public interface BannerService {

    @Nonnull
    List<BannerDetail> findAll() throws ServiceException;
}
