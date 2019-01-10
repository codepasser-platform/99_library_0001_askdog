package com.askdog.service.impl;

import com.askdog.model.data.inner.TargetType;
import com.askdog.model.data.inner.location.LocationDescription;
import com.askdog.service.LocationService;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.location.LocationRecorder;
import com.askdog.service.location.LocationAgent;
import com.askdog.service.location.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import static com.askdog.model.data.inner.location.LocationProvider.TENCENT_MAP;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    @Provider(TENCENT_MAP)
    private LocationAgent locationAgent;

    @Autowired
    private LocationRecorder locationRecorder;

    @Override
    public boolean analysis(@Nonnull TargetType type, @Nonnull String targetId, @Nonnull String ip, Double lat, Double lng) throws ServiceException {
        return locationAgent.analysis(type, targetId, ip, lat, lng);
    }

    @Nonnull
    @Override
    public LocationDescription[] suggestion(@Nonnull String ip, Double lat, Double lng) throws ServiceException {
        return locationAgent.suggestion(ip, lat, lng);
    }

    @Override
    public void residence(@Nonnull String userId) throws ServiceException {
        locationRecorder.generateUserResidence(userId);
    }

}
