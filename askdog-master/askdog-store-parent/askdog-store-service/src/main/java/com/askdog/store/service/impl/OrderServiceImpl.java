package com.askdog.store.service.impl;

import com.askdog.store.model.entity.Order;
import com.askdog.store.model.repository.OrderRepository;
import com.askdog.store.service.OrderService;
import com.askdog.store.service.bo.OrderDetail;
import com.askdog.store.service.bo.PureOrder;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.service.impl.cell.OrderCell;
import com.askdog.store.service.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

import static com.askdog.store.service.util.PageUtils.rePage;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderCell orderCell;

    @Autowired
    private OrderRepository orderRepository;

    @Nonnull
    @Override
    public Order create(@Nonnull String buyerId, @Nonnull String userId, @Nonnull PureOrder pureOrder) throws ServiceException {
        return orderCell.create(buyerId, userId, pureOrder);
    }

    @Nonnull
    @Override
    public Page<OrderDetail> find(@Nonnull String buyerId, @Nonnull Pageable paging) throws ServiceException {
        return rePage(orderRepository.findByOwner_Uuid(buyerId, paging), paging, t -> orderCell.findDetailStateLess(t.getUuid()));
    }

    @Nonnull
    @Override
    public OrderDetail find(@Nonnull String buyerId, @Nonnull String id) throws ServiceException {
        return orderCell.find(buyerId, id);
    }
}
