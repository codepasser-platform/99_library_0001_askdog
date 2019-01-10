package com.askdog.web.api;

import com.askdog.service.UserCouponService;
import com.askdog.service.UserService;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.usercoupon.UserCouponPageDetail;
import com.askdog.service.exception.ServiceException;
import com.askdog.web.configuration.userdetails.AdUserDetails;
import com.askdog.web.vo.UserCouponRequest;
import com.askdog.wechat.api.wxclient.WxClient;
import com.askdog.wechat.api.wxclient.exception.WxClientException;
import com.askdog.wechat.api.wxclient.model.TemplateNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.askdog.wechat.api.wxclient.model.TemplateNotice.DataValue;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/usercoupons")
public class UserCouponApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private WxClient wxClient;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = GET)
    public PagedData<UserCouponPageDetail> getUserCoupons(@AuthenticationPrincipal AdUserDetails user,
                                                          @PageableDefault(sort = "creationTime", direction = DESC) Pageable pageable) throws ServiceException {
        return userCouponService.getUserCoupons(user.getId(), pageable);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = POST)
    public UserCouponPageDetail gain(@AuthenticationPrincipal AdUserDetails user, @NotNull @RequestBody UserCouponRequest userCouponRequest) {
        UserCouponPageDetail userCouponPageDetail = userCouponService.gain(user.getId(), userCouponRequest.getProductId(), userCouponRequest.getCouponId());
        couponNotice(user, userCouponPageDetail);
        return userCouponPageDetail;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = PUT)
    public UserCouponPageDetail upgradeUserCoupon(@AuthenticationPrincipal AdUserDetails user, @NotNull @RequestBody UserCouponRequest userCouponRequest) {
        UserCouponPageDetail userCouponPageDetail = userCouponService.upgradeUserCoupon(user.getId(), userCouponRequest.getProductId(), userCouponRequest.getCouponId());
        couponNotice(user, userCouponPageDetail);
        return userCouponPageDetail;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = GET)
    public UserCouponPageDetail getUserCouponDetail(@AuthenticationPrincipal AdUserDetails user, @PathVariable("id") Long userCouponId) {
        return userCouponService.getPageDetail(user.getId(), userCouponId);
    }

    private void couponNotice(AdUserDetails user, UserCouponPageDetail userCouponPageDetail) {
        try {
            String openid = user.getDetails().get("openid");
            Map<String, DataValue> data = new HashMap<>();

            data.put("first", new DataValue("尊敬的" + user.getExternalUser().getNickname() + ": 优惠券已经放入您的账户中，点击\"优惠券\"即可查看。 ", "#000000"));
            data.put("keyword1", new DataValue(userCouponPageDetail.getStoreBasic().getName(), "#173177"));
            data.put("keyword2", new DataValue(userCouponPageDetail.getStoreBasic().getPhone(), "#173177"));
            data.put("keyword3", new DataValue(userCouponPageDetail.getStoreBasic().getAddress(), "#173177"));
            data.put("keyword4", new DataValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(userCouponPageDetail.getReceiveTime()), "#173177"));
            data.put("keyword5", new DataValue("￥" + userCouponPageDetail.getRule(), "#173177"));
            data.put("remark", new DataValue("使用说明：最终解释权归商户所有。为商户点赞，让我们服务更好。", "#000000"));

            wxClient.couponNotice().request(TemplateNotice.build(openid, userCouponPageDetail.getId(), data));
        } catch (WxClientException e) {
            e.printStackTrace();
        }
    }

}
