package com.askdog.store.service.impl;

import com.askdog.store.model.entity.Buyer;
import com.askdog.store.model.entity.builder.BuyerBuilder;
import com.askdog.store.model.repository.BuyerRepository;
import com.askdog.store.service.BuyerService;
import com.askdog.store.service.bo.BuyerDetail;
import com.askdog.store.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Nonnull
    @Override
    public Buyer save(@Nonnull BuyerDetail buyerDetail) throws ServiceException {
        Optional<Buyer> optional = buyerRepository.findByBuyerId(buyerDetail.getBuyerId());
        Buyer buyer;
        if (!optional.isPresent()) {
            buyer = BuyerBuilder.buyerBuilder().setBuyerId(buyerDetail.getBuyerId()).setName(buyerDetail.getName()).build();
            buyerRepository.save(buyer);
        } else {
            buyer = optional.get();
        }
        return buyer;
    }
}
