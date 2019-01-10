package com.askdog.store.web.api;

import com.askdog.store.model.entity.Order;
import com.askdog.store.service.OrderService;
import com.askdog.store.service.bo.BuyerDetail;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.web.api.annotation.RestApiPost;
import com.askdog.store.web.api.vo.PostedOrder;
import com.askdog.store.web.api.vo.PresentationOrder;
import com.askdog.store.web.api.vo.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.askdog.store.web.api.vo.common.PageResultHelper.rePage;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Autowired
    private OrderService orderService;

    @RestApiPost
    public Order create(@AuthenticationPrincipal BuyerDetail buyerDetail, @RequestBody PostedOrder postedOrder) throws ServiceException {
        return orderService.create(buyerDetail.getId(), buyerDetail.getBuyerId(), postedOrder.convert());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageResult<PresentationOrder> find(@AuthenticationPrincipal BuyerDetail buyerDetail, Pageable pageable) throws ServiceException {
        return rePage(orderService.find(buyerDetail.getId(), pageable), orderDetail -> new PresentationOrder().from(orderDetail));
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PresentationOrder find(@AuthenticationPrincipal BuyerDetail buyerDetail, @PathVariable("id") String id) throws ServiceException {
        return new PresentationOrder().from(orderService.find(buyerDetail.getId(), id));
    }

}
