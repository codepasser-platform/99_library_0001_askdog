package com.askdog.store.bootstrap.impl;

import com.askdog.store.bootstrap.DataCreator;
import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.Banner;
import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.Sku;
import com.askdog.store.model.entity.Buyer;
import com.askdog.store.model.entity.Category;
import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.entity.Store;
import com.askdog.store.model.repository.BuyerRepository;
import com.askdog.store.model.repository.CategoryRepository;
import com.askdog.store.model.repository.GoodsRepository;
import com.askdog.store.model.repository.StoreRepository;
import com.askdog.store.model.repository.mongo.AreaTreeRepository;
import com.askdog.store.model.repository.mongo.BannerRepository;
import com.askdog.store.model.repository.mongo.ResourceRecordRepository;
import com.askdog.store.model.repository.mongo.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class DefaultDataCreator implements DataCreator {


    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private ResourceRecordRepository resourceRecordRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private AreaTreeRepository areaTreeRepository;

    @Override
    public <T extends Area> DataCreator area(List<T> areas) {
        //noinspection unchecked
        areaTreeRepository.insertAll((Collection<Area>) areas);
        return this;
    }

    @Override
    public DataCreator buyer(Buyer buyer) {
        buyerRepository.save(buyer);
        return this;
    }

    @Override
    public DataCreator category(Category category) {
        categoryRepository.save(category);
        return this;
    }

    @Override
    public DataCreator store(Store store) {
        storeRepository.save(store);
        return this;
    }

    @Override
    public DataCreator goods(Goods goods) {
        goodsRepository.save(goods);
        return this;
    }

    @Override
    public DataCreator sku(Sku sku) {
        skuRepository.save(sku);
        return this;
    }


    @Override
    public DataCreator goodsResource(ResourceRecord resourceRecord) {
        resourceRecordRepository.save(resourceRecord);
        return this;
    }

    @Override
    public DataCreator banner(Banner banner) {
        bannerRepository.save(banner);
        return this;
    }

    @Override
    public DataCreator bannerResource(ResourceRecord resourceRecord) {
        resourceRecordRepository.save(resourceRecord);
        return this;
    }
}