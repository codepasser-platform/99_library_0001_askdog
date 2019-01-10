package com.askdog.store.bootstrap;

import com.askdog.store.model.data.Banner;
import com.askdog.store.model.data.Sku;
import com.askdog.store.model.data.builder.BannerBuilder;
import com.askdog.store.model.data.builder.ResourceRecordBuilder;
import com.askdog.store.model.data.builder.SkuBuilder;
import com.askdog.store.model.data.inner.TargetType;
import com.askdog.store.model.entity.Category;
import com.askdog.store.model.entity.Goods;
import com.askdog.store.model.entity.Store;
import com.askdog.store.model.entity.builder.BuyerBuilder;
import com.askdog.store.model.entity.builder.GoodsBuilder;
import com.askdog.store.model.entity.builder.StoreBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Arrays;

import static com.askdog.common.RunningProfile.INITIALIZE;
import static com.askdog.store.model.data.builder.ResourceRecordBuilder.resourceRecordBuilder;
import static com.askdog.store.model.data.inner.TargetType.BANNER;
import static com.askdog.store.model.data.inner.TargetType.GOODS;
import static com.askdog.store.model.data.inner.TargetType.GOODS_DETAIL;
import static com.askdog.store.model.entity.builder.CategoryBuilder.categoryBuilder;
import static com.askdog.store.model.entity.builder.GoodsBuilder.goodsBuilder;

@Service
@Profile(INITIALIZE)
public class DatabaseInitializer {

    @Autowired
    private DataReader dataReader;

    @Autowired
    private DataCreator dataCreator;

    @PostConstruct
    private void initialize() {

        /** area data **/
        dataCreator.area(dataReader.province("/var/local/askdog/deploy/positionJson/1-province"));
        dataCreator.area(dataReader.city("/var/local/askdog/deploy/positionJson/2-city"));
        dataCreator.area(dataReader.county("/var/local/askdog/deploy/positionJson/3-county"));
        /*
        dataCreator.area(dataReader.town("/var/local/askdog/deploy/positionJson/4-town"));
        dataCreator.area(dataReader.village("/var/local/askdog/deploy/positionJson/5-village"));
        */

        /** test buyer **/
        dataCreator.buyer(BuyerBuilder.buyerBuilder().setBuyerId("0000-0000-0000-0000").setName("askdog").build());

        /** category **/
        Category category_0 = categoryBuilder().setName("食品").setSummary("食品").setLevel(0).setDisplay(true).setDisplayOrder(0).build();
        Category category_0_0 = categoryBuilder().setName("休闲食品").setSummary("休闲食品").setLevel(1).setDisplay(true).setDisplayOrder(0).setParent(category_0.getUuid()).build();
        Category category_0_0_0 = categoryBuilder().setName("巧克力").setSummary("巧克力").setLevel(2).setDisplay(true).setDisplayOrder(0).setParent(category_0_0.getUuid()).build();

        Category category_1 = categoryBuilder().setName("居家日用/节日用品").setSummary("食品").setLevel(0).setDisplay(true).setDisplayOrder(1).build();
        Category category_1_0 = categoryBuilder().setName("口罩/眼罩/防护用品").setSummary("口罩/眼罩/防护用品").setLevel(1).setDisplay(true).setDisplayOrder(0).setParent(category_1.getUuid()).build();
        Category category_1_0_0 = categoryBuilder().setName("眼罩").setSummary("眼罩").setLevel(2).setDisplay(true).setDisplayOrder(0).setParent(category_1_0.getUuid()).build();

        Category category_2 = categoryBuilder().setName("饰品").setSummary("饰品").setLevel(0).setDisplay(true).setDisplayOrder(2).build();
        Category category_2_0 = categoryBuilder().setName("眼镜").setSummary("眼镜").setLevel(1).setDisplay(true).setDisplayOrder(0).setParent(category_2.getUuid()).build();
        Category category_2_0_0 = categoryBuilder().setName("太阳镜").setSummary("太阳镜").setLevel(2).setDisplay(true).setDisplayOrder(0).setParent(category_2_0.getUuid()).build();

        dataCreator.category(category_0);
        dataCreator.category(category_0_0);
        dataCreator.category(category_0_0_0);
        dataCreator.category(category_1);
        dataCreator.category(category_1_0);
        dataCreator.category(category_1_0_0);
        dataCreator.category(category_2);
        dataCreator.category(category_2_0);
        dataCreator.category(category_2_0_0);

        /** store **/
        Store store = StoreBuilder.storeBuilder().setName("askdog").build();
        dataCreator.store(store);

        /** goods **/
        Goods goods1 = goodsBuilder().setName("CONTINENTE巧克力").setSummary("葡萄牙牛奶巧克力(NEGRO)品牌").setPrice(10000L).setPointsPrice(200L).setDiscount(0).setStock(999L).setSales(0L).setCategory(category_0_0_0.getUuid()).setOwner(store.getUuid()).build();
        Goods goods2 = goodsBuilder().setName("CONTINENTE巧克力").setSummary("葡萄牙牛奶巧克力(AMENDOAS)品牌").setPrice(10000L).setPointsPrice(200L).setDiscount(0).setStock(999L).setSales(0L).setCategory(category_0_0_0.getUuid()).setOwner(store.getUuid()).build();
        Goods goods3 = goodsBuilder().setName("CONTINENTE巧克力").setSummary("葡萄牙牛奶巧克力(LEITE)品牌").setPrice(10000L).setPointsPrice(200L).setDiscount(0).setStock(999L).setSales(0L).setCategory(category_0_0_0.getUuid()).setOwner(store.getUuid()).build();
        Goods goods4 = goodsBuilder().setName("热敷蒸汽眼罩").setSummary("云南白药热敷蒸汽眼罩湿润紧肤").setPrice(10000L).setPointsPrice(200L).setDiscount(0).setStock(999L).setSales(0L).setCategory(category_1_0_0.getUuid()).setOwner(store.getUuid()).build();
        Goods goods5 = goodsBuilder().setName("男款太阳镜").setSummary("宋仲基同款驾驶镜飞行员系列").setPrice(10000L).setPointsPrice(200L).setDiscount(0).setStock(999L).setSales(0L).setCategory(category_2_0_0.getUuid()).setOwner(store.getUuid()).build();
        Goods goods6 = goodsBuilder().setName("女款太阳镜").setSummary("范冰冰同款墨镜防紫外线太阳镜").setPrice(10000L).setPointsPrice(200L).setDiscount(0).setStock(999L).setSales(0L).setCategory(category_2_0_0.getUuid()).setOwner(store.getUuid()).build();
        dataCreator.goods(goods1);
        dataCreator.goods(goods2);
        dataCreator.goods(goods3);
        dataCreator.goods(goods4);
        dataCreator.goods(goods5);
        dataCreator.goods(goods6);
        /** goods sku **/
        dataCreator.sku(SkuBuilder.skuBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS).setSkuItems(Arrays.asList(new Sku.SkuItem("CONTINENTE生产厂商", "SONAE"), new Sku.SkuItem("生产国", "葡萄牙"), new Sku.SkuItem("净重", "100g"), new Sku.SkuItem("描述", "本产品使用不低于25%可可粉，辅以葡萄牙原生态牛奶精致而成，口感纯滑柔和，可可与牛奶的完美结合"))).build());
        dataCreator.sku(SkuBuilder.skuBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS).setSkuItems(Arrays.asList(new Sku.SkuItem("CONTINENTE生产厂商", "SONAE"), new Sku.SkuItem("生产国", "葡萄牙"), new Sku.SkuItem("净重", "100g"), new Sku.SkuItem("描述", "本产品使用不低于50%可可粉，辅以葡萄牙原生态牛奶精致而成，口感纯滑柔和，可可与牛奶的完美结合"))).build());
        dataCreator.sku(SkuBuilder.skuBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS).setSkuItems(Arrays.asList(new Sku.SkuItem("CONTINENTE生产厂商", "SONAE"), new Sku.SkuItem("生产国", "葡萄牙"), new Sku.SkuItem("净重", "100g"), new Sku.SkuItem("描述", "本产品使用不低于25%可可粉，辅以葡萄牙原生态牛奶精致而成，口感纯滑柔和，可可与牛奶的完美结合"))).build());
        dataCreator.sku(SkuBuilder.skuBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS).setSkuItems(Arrays.asList(new Sku.SkuItem("组成", "由无纺布眼罩、发热体和茉莉花提取物构成"), new Sku.SkuItem("规格", "185mm X 80mm"))).build());
        dataCreator.sku(SkuBuilder.skuBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS).setSkuItems(Arrays.asList(new Sku.SkuItem("品牌", "爱自拍"), new Sku.SkuItem("生产商", "深圳天视能眼镜有限公司"), new Sku.SkuItem("许可证号", "XK16-003-01587"), new Sku.SkuItem("类别", "遮阳镜"))).build());
        dataCreator.sku(SkuBuilder.skuBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS).setSkuItems(Arrays.asList(new Sku.SkuItem("品牌", "爱自拍"), new Sku.SkuItem("生产商", "临海市康波眼镜有限公司"), new Sku.SkuItem("许可证号", "XK16-003-01612"), new Sku.SkuItem("类别", "遮阳镜"))).build());

        /** goods avatar**/
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/01/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/01/2.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/01/3.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/02/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/02/2.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/02/3.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/03/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/03/2.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/03/3.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/04/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/04/2.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/04/3.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/05/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/05/2.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/05/3.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/06/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/06/2.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS).setPersistenceName("http://store.askdog.com/styles/images/goods/06/3.png").build());

        /** goods detail**/
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/01/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/01/2.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/02/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/02/2.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/03/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/03/2.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/04/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/04/2.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/05/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/05/2.png").build());

        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/06/1.png").build());
        dataCreator.goodsResource(resourceRecordBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS_DETAIL).setPersistenceName("http://store.askdog.com/styles/images/detail/06/2.png").build());

        /** banner **/
        Banner banner1 = BannerBuilder.bannerBuilder().setTarget(goods1.getUuid()).setTargetType(GOODS).build();
        Banner banner2 = BannerBuilder.bannerBuilder().setTarget(goods2.getUuid()).setTargetType(GOODS).build();
        Banner banner3 = BannerBuilder.bannerBuilder().setTarget(goods3.getUuid()).setTargetType(GOODS).build();
        Banner banner4 = BannerBuilder.bannerBuilder().setTarget(goods4.getUuid()).setTargetType(GOODS).build();
        Banner banner5 = BannerBuilder.bannerBuilder().setTarget(goods5.getUuid()).setTargetType(GOODS).build();
        Banner banner6 = BannerBuilder.bannerBuilder().setTarget(goods6.getUuid()).setTargetType(GOODS).build();
        dataCreator.banner(banner1);
        dataCreator.banner(banner2);
        dataCreator.banner(banner3);
        dataCreator.banner(banner4);
        dataCreator.banner(banner5);
        dataCreator.banner(banner6);

        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner1.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/1.png").build());
        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner1.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/1_1.png").build());

        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner2.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/2.png").build());
        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner2.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/2_1.png").build());

        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner3.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/3.png").build());
        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner3.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/3_1.png").build());

        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner4.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/4.png").build());
        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner4.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/4_1.png").build());

        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner5.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/5.png").build());
        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner5.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/5_1.png").build());

        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner6.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/6.png").build());
        dataCreator.bannerResource(resourceRecordBuilder().setTarget(banner6.getId()).setTargetType(BANNER).setPersistenceName("http://store.askdog.com/styles/images/banner/6_1.png").build());

    }

}
