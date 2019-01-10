package com.askdog.store.web.api;

import com.askdog.store.service.bo.BuyerDetail;
import com.askdog.store.service.exception.ServiceException;
import com.askdog.store.service.impl.cell.OrderCell.WebConfig;
import com.askdog.store.web.api.vo.BuyerSelf;
import com.askdog.store.web.api.vo.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.EnumSet;

import static com.askdog.store.web.api.vo.UserStatus.ANONYMOUS;
import static com.askdog.store.web.api.vo.UserStatus.AUTHENTICATED;

@RestController
@RequestMapping("/api")
public class BuyerApi {

    @Autowired
    private WebConfig webconfig;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public BuyerSelf me(@AuthenticationPrincipal BuyerDetail buyerDetail) throws ServiceException {
        // TODO: 16-7-14
        BuyerDetail detail = getRestTemplate().getForEntity(webconfig.getMasterUrl() + "/oauth/userinfo/" + buyerDetail.getBuyerId(), BuyerDetail.class).getBody();
        buyerDetail.setPoints(detail.getPoints());
        return new BuyerSelf().from(buyerDetail);
    }

    @RequestMapping(value = "/me/status", method = RequestMethod.GET)
    @ResponseBody
    public EnumSet<UserStatus> status(@AuthenticationPrincipal Authentication authentication) {
        return authentication == null ? EnumSet.of(ANONYMOUS) : EnumSet.of(AUTHENTICATED);
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
