package com.askdog.service;

import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.inner.location.LocationDescription;
import com.askdog.service.exception.ServiceException;

import javax.annotation.Nonnull;

public interface LocationService {

    boolean analysis(@Nonnull TargetType type, @Nonnull String targetId, @Nonnull String ip, Double lat, Double lng) throws ServiceException;

    @Nonnull
    LocationDescription[] suggestion(@Nonnull String ip, Double lat, Double lng) throws ServiceException;

    void residence(@Nonnull String userId) throws ServiceException;

}
