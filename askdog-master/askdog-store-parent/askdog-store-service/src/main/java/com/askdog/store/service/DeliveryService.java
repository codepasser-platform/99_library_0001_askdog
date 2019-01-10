package com.askdog.store.service;

import com.askdog.store.service.bo.AmendedDelivery;
import com.askdog.store.service.bo.DeliveryDetail;
import com.askdog.store.service.bo.PureDelivery;
import com.askdog.store.service.exception.ServiceException;

import javax.annotation.Nonnull;
import java.util.List;

public interface DeliveryService {

    @Nonnull
    List<DeliveryDetail> find(@Nonnull String buyerId) throws ServiceException;

    @Nonnull
    DeliveryDetail create(@Nonnull String buyerId, @Nonnull PureDelivery pureDelivery) throws ServiceException;

    @Nonnull
    DeliveryDetail update(@Nonnull String buyerId, @Nonnull String id, @Nonnull AmendedDelivery amendedDelivery) throws ServiceException;

    @Nonnull
    DeliveryDetail delete(@Nonnull String buyerId, @Nonnull String id) throws ServiceException;
}
