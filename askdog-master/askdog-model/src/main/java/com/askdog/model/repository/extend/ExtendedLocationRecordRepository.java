package com.askdog.model.repository.extend;

import com.askdog.model.data.UserLocation;
import com.askdog.model.data.UserResidence;

import javax.annotation.Nonnull;
import java.util.List;

public interface ExtendedLocationRecordRepository {

    List<UserLocation> findNearbyUsers(@Nonnull String userId, @Nonnull Double lat, @Nonnull Double lng, double nearbyDistance, long nearbyLimit);

    List<UserResidence> findUserLocation(@Nonnull String userId, long recentLimit);

    UserResidence countNearbyPoints(@Nonnull String userId, @Nonnull Double lat, @Nonnull Double lng, double rateDistance);
}
