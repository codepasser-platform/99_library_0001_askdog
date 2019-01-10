package com.askdog.store.service;

import com.askdog.store.model.entity.Buyer;
import com.askdog.store.service.bo.BuyerDetail;
import com.askdog.store.service.exception.ServiceException;

import javax.annotation.Nonnull;

public interface BuyerService {

    @Nonnull
    Buyer save(@Nonnull BuyerDetail buyerDetail) throws ServiceException;
}
