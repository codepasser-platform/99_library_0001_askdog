package com.askdog.service.proxy;

import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.inner.location.LocationDescription;
import com.askdog.service.LocationService;
import com.askdog.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class LocationServiceImpl implements LocationService {

    @Override
    public boolean analysis(@Nonnull TargetType type, @Nonnull String targetId, @Nonnull String ip, Double lat, Double lng) throws ServiceException {
        return false;
    }

    @Nonnull
    @Override
    public LocationDescription[] suggestion(@Nonnull String ip, Double lat, Double lng) throws ServiceException {
        return new LocationDescription[0];
    }

    @Override
    public void residence(@Nonnull String userId) throws ServiceException {

    }
}
