package com.askdog.store.service.impl;

import com.askdog.store.service.BannerService;
import com.askdog.store.service.bo.BannerDetail;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.service.impl.cell.BannerCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerCell bannerCell;

    @Nonnull
    @Override
    public List<BannerDetail> findAll() throws ServiceException {
        return bannerCell.getAll();
    }
}
