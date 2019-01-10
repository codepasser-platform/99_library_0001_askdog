package com.askdog.store.web.api;

import com.askdog.store.service.DeliveryService;
import com.askdog.store.service.bo.AmendedDelivery;
import com.askdog.store.service.bo.BuyerDetail;
import com.askdog.store.service.bo.DeliveryDetail;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.web.api.annotation.RestApiDelete;
import com.askdog.store.web.api.annotation.RestApiPost;
import com.askdog.store.web.api.annotation.RestApiPut;
import com.askdog.store.web.api.vo.PostedDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryApi {

    @Autowired
    private DeliveryService deliveryService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<DeliveryDetail> find(@AuthenticationPrincipal BuyerDetail buyerDetail) throws ServiceException {
        return deliveryService.find(buyerDetail.getId());
    }

    @RestApiPost
    public DeliveryDetail create(@AuthenticationPrincipal BuyerDetail buyerDetail, @RequestBody PostedDelivery postedDelivery) throws ServiceException {
        return deliveryService.create(buyerDetail.getId(), postedDelivery.convert());
    }

    @RestApiPut
    public DeliveryDetail update(@AuthenticationPrincipal BuyerDetail buyerDetail, @PathVariable String id, @RequestBody AmendedDelivery amendedDelivery) throws ServiceException {
        return deliveryService.update(buyerDetail.getId(), id, amendedDelivery);
    }

    @RestApiDelete
    public DeliveryDetail delete(@AuthenticationPrincipal BuyerDetail buyerDetail, @PathVariable String id) throws ServiceException {
        return deliveryService.delete(buyerDetail.getId(), id);
    }

}
