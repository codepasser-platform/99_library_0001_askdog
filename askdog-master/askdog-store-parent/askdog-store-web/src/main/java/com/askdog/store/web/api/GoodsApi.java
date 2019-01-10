package com.askdog.store.web.api;

import com.askdog.store.service.GoodsService;
import com.askdog.store.service.bo.GoodsDetail;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.web.api.annotation.RestApiGet;
import com.askdog.store.web.api.vo.PresentationGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/goods")
public class GoodsApi {

    @Autowired
    private GoodsService goodsService;

    @RestApiGet
    public PresentationGoods get(@PathVariable("id") String id) throws ServiceException {
        return new PresentationGoods().from(goodsService.find(id));
    }

}
