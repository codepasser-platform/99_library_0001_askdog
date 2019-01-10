package com.askdog.store.service;

import com.askdog.store.model.entity.Order;
import com.askdog.store.service.bo.OrderDetail;
import com.askdog.store.service.bo.PureOrder;
import com.askdog.store.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;

public interface OrderService {

    @Nonnull
    Order create(@Nonnull String buyerId, @Nonnull String userId, @Nonnull PureOrder pureOrder) throws ServiceException;

    @Nonnull
    Page<OrderDetail> find(@Nonnull String buyerId, @Nonnull Pageable paging) throws ServiceException;

    @Nonnull
    OrderDetail find(@Nonnull String buyerId, @Nonnull String id) throws ServiceException;

}
