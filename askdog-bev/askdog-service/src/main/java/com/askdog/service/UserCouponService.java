package com.askdog.service;

import com.askdog.service.bo.usercoupon.UserCouponDetail;
import com.askdog.service.bo.admin.dashboard.CouponStatistics;
import com.askdog.service.bo.common.PagedData;
import com.askdog.service.bo.usercoupon.UserCouponPageDetail;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import static com.askdog.service.utils.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@FeignClient("service")
@RequestMapping("/service/coupon")
public interface UserCouponService {

    @Nonnull
    @RequestMapping(value = "/usercoupon", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    UserCouponPageDetail gain(@NotNull @RequestParam("userId") long userId, @NotNull @RequestParam("productId") long productId, @NotNull @RequestParam("couponId") long couponId);

    @Nonnull
    @RequestMapping(value = "/usercoupon", method = PUT, produces = APPLICATION_JSON_UTF8_VALUE)
    UserCouponPageDetail upgradeUserCoupon(@NotNull @RequestParam("userId") long userId, @NotNull @RequestParam("productId") long productId, @NotNull @RequestParam("couponId") long couponId);

    @RequestMapping(value = "/usercoupon/{id}", method = DELETE)
    void deleteUserCoupon(@RequestParam("userId") long userId, @PathVariable("id") long id);

    @Nonnull
    @RequestMapping(value = "/user/{id}/detail", method = GET)
    UserCouponDetail getDetail(@RequestParam("userId") long userId, @PathVariable("id") long id);

    @Nonnull
    @RequestMapping(value = "/user/{id}/page_detail", method = GET)
    UserCouponPageDetail getPageDetail(@RequestParam("userId") long userId, @PathVariable("id") long id);

    @Nonnull
    @RequestMapping(value = "/user", method = POST, produces = APPLICATION_JSON_UTF8_VALUE)
    PagedData<UserCouponPageDetail> getUserCoupons(@RequestParam(name = "userId") Long userId, @RequestBody() Pageable pageable);

    @RequestMapping(value = "/statistic", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    CouponStatistics couponStatistic();
}
