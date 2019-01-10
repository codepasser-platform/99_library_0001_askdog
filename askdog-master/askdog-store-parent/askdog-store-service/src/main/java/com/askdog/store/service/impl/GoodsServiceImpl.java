package com.askdog.store.service.impl;

import com.askdog.store.service.GoodsService;
import com.askdog.store.service.bo.GoodsDetail;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.service.impl.cell.GoodsCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsCell goodsCell;

    @Nonnull
    @Override
    public GoodsDetail find(@Nonnull String uuid) throws ServiceException {
        return goodsCell.findDetail(uuid);
    }
}
