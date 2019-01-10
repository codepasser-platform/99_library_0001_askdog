package com.askdog.store.service.impl;

import com.askdog.store.model.data.Delivery;
import com.askdog.store.model.data.builder.DeliveryBuilder;
import com.askdog.store.model.repository.mongo.DeliveryRepository;
import com.askdog.store.service.DeliveryService;
import com.askdog.store.service.bo.AmendedDelivery;
import com.askdog.store.service.bo.DeliveryDetail;
import com.askdog.store.service.bo.PureDelivery;
import com.askdog.store.service.exception.NotFoundException;
import com.askdog.store.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

import static com.askdog.store.model.data.inner.TargetType.BUYER;
import static com.askdog.store.service.exception.NotFoundException.Error.DELIVERY;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Nonnull
    @Override
    public List<DeliveryDetail> find(@Nonnull String buyerId) throws ServiceException {
        List<Delivery> deliveries = deliveryRepository.findByTargetAndTargetType(buyerId, BUYER);
        return deliveries.stream().map((delivery) -> new DeliveryDetail().from(delivery)).collect(Collectors.toList());
    }

    @Nonnull
    @Override
    public DeliveryDetail create(@Nonnull String buyerId, @Nonnull PureDelivery pureDelivery) throws ServiceException {
        Delivery delivery = DeliveryBuilder.deliveryBuilder()
                .setRecipient(pureDelivery.getRecipient())
                .setPhoneNumber(pureDelivery.getPhoneNumber())
                .setProvinceId(pureDelivery.getProvinceId())
                .setProvince(pureDelivery.getProvince())
                .setCityId(pureDelivery.getCityId())
                .setCity(pureDelivery.getCity())
                .setCountyId(pureDelivery.getCountyId())
                .setCounty(pureDelivery.getCounty())
                .setAddress(pureDelivery.getAddress())
                .setLabel(pureDelivery.getLabel())
                .setTarget(buyerId)
                .setTargetType(BUYER)
                .setDefault(false) // TODO: default delivery
                .build();
        deliveryRepository.save(delivery);
        return new DeliveryDetail().from(delivery);
    }

    @Nonnull
    @Override
    public DeliveryDetail update(@Nonnull String buyerId, @Nonnull String id, @Nonnull AmendedDelivery amendedDelivery) throws ServiceException {
        Delivery delivery = deliveryRepository.findByIdAndTarget(id, buyerId).orElseThrow(() -> new NotFoundException(DELIVERY));
        delivery.setRecipient(amendedDelivery.getRecipient());
        delivery.setPhoneNumber(amendedDelivery.getPhoneNumber());
        delivery.setProvinceId(amendedDelivery.getProvinceId());
        delivery.setProvince(amendedDelivery.getProvince());
        delivery.setCityId(amendedDelivery.getCityId());
        delivery.setCity(amendedDelivery.getCity());
        delivery.setCountyId(amendedDelivery.getCountyId());
        delivery.setCounty(amendedDelivery.getCounty());
        delivery.setAddress(amendedDelivery.getAddress());
        delivery.setLabel(amendedDelivery.getLabel());
        delivery.setDefault(false); // TODO: default delivery
        deliveryRepository.save(delivery);
        return new DeliveryDetail().from(delivery);
    }

    @Nonnull
    @Override
    public DeliveryDetail delete(@Nonnull String buyerId, @Nonnull String id) throws ServiceException {
        Delivery delivery = deliveryRepository.findByIdAndTarget(id, buyerId).orElseThrow(() -> new NotFoundException(DELIVERY));
        deliveryRepository.delete(delivery.getId());
        return new DeliveryDetail().from(delivery);
    }
}
