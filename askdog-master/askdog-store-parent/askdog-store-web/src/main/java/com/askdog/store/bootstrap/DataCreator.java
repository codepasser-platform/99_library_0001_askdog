package com.askdog.store.bootstrap;

import com.askdog.store.model.data.Area;
import com.askdog.store.model.data.Banner;
import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.data.Sku;
import com.askdog.store.model.entity.Buyer;
import com.askdog.store.model.entity.Category;
import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.entity.Store;

import java.util.List;

public interface DataCreator {

    <T extends Area> DataCreator area(List<T> areas);

    DataCreator buyer(Buyer buyer);

    DataCreator category(Category category);

    DataCreator store(Store store);

    DataCreator goods(Goods goods);

    DataCreator sku(Sku sku);

    DataCreator goodsResource(ResourceRecord resourceRecord);

    DataCreator banner(Banner banner);

    DataCreator bannerResource(ResourceRecord resourceRecord);

}
