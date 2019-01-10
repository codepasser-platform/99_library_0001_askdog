package com.askdog.store.service.impl.cell;

import com.askdog.store.model.data.Banner;
import com.askdog.store.model.data.ResourceRecord;
import com.askdog.store.model.repository.GoodsRepository;
import com.askdog.store.model.repository.mongo.BannerRepository;
import com.askdog.store.model.repository.mongo.ResourceRecordRepository;
import com.askdog.store.service.bo.BannerDetail;
import com.askdog.store.service.bo.GoodsDetail;
import com.askdog.store.service.exception.NotFoundException;
import com.askdog.store.service.exception.ServiceException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

import static com.askdog.store.model.data.inner.TargetType.BANNER;

@Component
public class BannerCell {

    @Autowired
    private GoodsCell goodsCell;

    @Autowired
    private ResourceRecordRepository resourceRecordRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Nonnull
    public List<BannerDetail> getAll() throws ServiceException {

        List<BannerDetail> bannerDetails = Lists.newArrayList();
        // find the banner record
        List<Banner> banners = bannerRepository.findAll();
        for (Banner banner : banners) {
            List<ResourceRecord> bannerResources = resourceRecordRepository.findByTargetAndTargetType(banner.getId(), BANNER);
            try {
                //TODO banner images
                if (bannerResources.size() == 2) {
                    bannerDetails.add(new BannerDetail().setScreenPicture(bannerResources.get(0).getPersistenceName()).setGoodsPicture(bannerResources.get(1).getPersistenceName()).from(goodsCell.findDetail(banner.getTarget())));
                }
            } catch (NotFoundException e) {
                // TODO: 7/5/16 banner old data
            }
        }
        return bannerDetails;
    }
}
