package com.askdog.store.service.impl.cell;

import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.Sku;
import com.askdog.store.model.data.Sku.SkuItem;
import com.askdog.store.model.data.inner.TargetType;
import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.repository.GoodsRepository;
import com.askdog.store.model.repository.mongo.ResourceRecordRepository;
import com.askdog.store.model.repository.mongo.SkuRepository;
import com.askdog.store.service.bo.GoodsDetail;
import com.askdog.store.service.exception.NotFoundException;
import com.askdog.store.service.exception.ServiceException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.askdog.store.service.exception.NotFoundException.Error.GOODS;
import static java.util.stream.Collectors.toList;

@Component
public class GoodsCell {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ResourceRecordRepository resourceRecordRepository;

    @Autowired
    private SkuRepository skuRepository;

    public Goods checkExist(@Nonnull String uuid) throws NotFoundException {
        return goodsRepository.findByUuid(uuid).orElseThrow(() -> new NotFoundException(GOODS));
    }

    public GoodsDetail findDetail(@Nonnull String uuid) throws ServiceException {
        Goods goods = this.checkExist(uuid);
        Optional<Sku> optional = skuRepository.findByTargetAndTargetType(uuid, TargetType.GOODS);

        List<SkuItem> skuItems = Lists.newArrayList();
        if (optional.isPresent()) {
            skuItems = optional.get().getSkuItems();
        }
        List<ResourceRecord> resourceAvatars = resourceRecordRepository.findByTargetAndTargetType(uuid, TargetType.GOODS);
        List<ResourceRecord> resourcePictures = resourceRecordRepository.findByTargetAndTargetType(uuid, TargetType.GOODS_DETAIL);
        return new GoodsDetail().from(goods)
                .setAvatars(resourceAvatars.stream().map(ResourceRecord::getPersistenceName).collect(toList()))
                .setPictures(resourcePictures.stream().map(ResourceRecord::getPersistenceName).collect(Collectors.toList()))
                .setSkuItems(skuItems);
    }
}
