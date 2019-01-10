package com.askdog.store.service.impl.cell;

import com.askdog.store.model.data.Delivery;
import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.builder.DeliveryBuilder;
import com.askdog.store.model.data.inner.TargetType;
import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.entity.Order;
import com.askdog.store.model.entity.OrderItem;
import com.askdog.store.model.entity.builder.OrderBuilder;
import com.askdog.store.model.entity.builder.OrderItemBuilder;
import com.askdog.store.model.repository.GoodsRepository;
import com.askdog.store.model.repository.OrderItemRepository;
import com.askdog.store.model.repository.OrderRepository;
import com.askdog.store.model.repository.mongo.DeliveryRepository;
import com.askdog.store.model.repository.mongo.ResourceRecordRepository;
import com.askdog.store.service.bo.*;
import com.askdog.store.service.exception.ForbiddenException;
import com.askdog.store.service.exception.NotFoundException;
import com.askdog.store.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.util.List;

import static com.askdog.store.service.exception.ForbiddenException.Error.DEDUCTION_POINTS;
import static com.askdog.store.service.exception.NotFoundException.Error.*;
import static java.util.stream.Collectors.toList;

@Component
@Transactional
public class OrderCell {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ResourceRecordRepository resourceRecordRepository;

    @Autowired
    private WebConfig webConfig;

    @Nonnull
    public Order create(@Nonnull String buyerId, @Nonnull String userId, @Nonnull PureOrder pureOrder) throws ServiceException {

        Delivery buyerDelivery = deliveryRepository.findByIdAndTarget(pureOrder.getDeliveryId(), buyerId).orElseThrow(() -> new NotFoundException(DELIVERY));

        Goods goods = goodsRepository.findByUuid(pureOrder.getGoodsId()).orElseThrow(() -> new NotFoundException(GOODS));

        // TODO: 16-7-14 deduction points
        long TotalPoints = goods.getPointsPrice() * pureOrder.getQuantity();
        try {
            getRestTemplate().put(webConfig.getMasterUrl() + "/oauth/userinfo/" + userId + "?points=" + TotalPoints, null);
        } catch (HttpClientErrorException e) {
            throw new ForbiddenException(DEDUCTION_POINTS);
        }
        Order order = OrderBuilder.orderBuilder()
                .setStatus(Order.Status.PAYMENT)
                .setOwner(buyerId).build();

        OrderItem orderItem = OrderItemBuilder.orderItemBuilder()
                .setGoods(pureOrder.getGoodsId())
                .setQuantity(pureOrder.getQuantity())
                .setOwner(order.getUuid()).build();

        Delivery orderDeliver = DeliveryBuilder.deliveryBuilder()
                .setRecipient(buyerDelivery.getRecipient())
                .setPhoneNumber(buyerDelivery.getPhoneNumber())
                .setProvinceId(buyerDelivery.getProvinceId())
                .setProvince(buyerDelivery.getProvince())
                .setCityId(buyerDelivery.getCityId())
                .setCity(buyerDelivery.getCity())
                .setCountyId(buyerDelivery.getCountyId())
                .setCounty(buyerDelivery.getCounty())
                .setAddress(buyerDelivery.getAddress())
                .setLabel(buyerDelivery.getLabel())
                .setTarget(order.getUuid())
                .setTargetType(TargetType.ORDER)
                .setDefault(false) // TODO: default delivery
                .build();
        deliveryRepository.save(orderDeliver);
        orderRepository.save(order);
        orderItemRepository.save(orderItem);

        // TODO: 7/5/16 SOA call points change

        return order;
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @Nonnull
    public OrderDetail findDetailStateLess(@Nonnull String id) throws ServiceException {
        Order order = orderRepository.findByUuid(id).orElseThrow(() -> new NotFoundException(ORDER));
        return innerDecorate(new OrderDetail().from(order));
    }

    @Nonnull
    public OrderDetail find(@Nonnull String buyerId, @Nonnull String id) throws ServiceException {
        Order order = orderRepository.findByOwner_UuidAndUuid(buyerId, id).orElseThrow(() -> new NotFoundException(ORDER));
        return innerDecorate(new OrderDetail().from(order));
    }

    private OrderDetail innerDecorate(OrderDetail orderDetail) {
        // order delivery
        List<Delivery> orderDelivery = deliveryRepository.findByTargetAndTargetType(orderDetail.getId(), TargetType.ORDER);
        OrderDetail detail = orderDetail;
        if (orderDelivery.size() > 0) {
            detail.setDelivery(new DeliveryDetail().from(orderDelivery.get(0)));
        }
        List<OrderItemDetail> orderItems = detail.getOrderItems();
        for (int index = 0; index < orderItems.size(); index++) {
            OrderItemDetail orderItem = orderItems.get(index);
            GoodsDetail goodsDetail = orderItem.getGoods();
            //goods avatars
            List<ResourceRecord> resourceAvatars = resourceRecordRepository.findByTargetAndTargetType(goodsDetail.getId(), TargetType.GOODS);
            goodsDetail.setAvatars(resourceAvatars.stream().map(ResourceRecord::getPersistenceName).collect(toList()));
            // TODO: Order:goods = 1:1 then set goods's first avatar
            if (index == 0) {
                detail.setAvatar(goodsDetail.getAvatars().get(0));
            }
        }
        return detail;
    }

    @Configuration
    @ConfigurationProperties("askdog")
    public static class WebConfig {

        private String masterUrl;

        public String getMasterUrl() {
            return masterUrl;
        }

        public void setMasterUrl(String masterUrl) {
            this.masterUrl = masterUrl;
        }
    }
}
