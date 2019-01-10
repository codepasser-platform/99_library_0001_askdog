package com.askdog.service.impl.cell;

import com.askdog.dao.repository.UserCouponRepository;
import com.askdog.model.entity.UserCoupon;
import com.askdog.service.bo.usercoupon.UserCouponBasic;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.impl.cache.annotation.usercoupon.UserCouponBasicCache;
import com.askdog.service.impl.cache.annotation.usercoupon.UserCouponBasicCacheRefresh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.askdog.service.exception.NotFoundException.Error.USER_COUPON;

@Component
public class UserCouponCell {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @UserCouponBasicCache
    public UserCouponBasic getBasic(Long userCouponId) {
        UserCoupon userCoupon = findExist(userCouponId);
        UserCouponBasic userCouponBasic = new UserCouponBasic();
        userCouponBasic.setId(userCouponId);
        userCouponBasic.setCreationTime(userCoupon.getCreationTime());
        userCouponBasic.setUseTime(userCoupon.getUseTime());
        userCouponBasic.setCouponId(userCoupon.getCoupon().getId());
        userCouponBasic.setProductId(userCoupon.getProduct().getId());
        userCouponBasic.setState(userCoupon.getState());
        userCouponBasic.setUserId(userCoupon.getUser().getId());
        return userCouponBasic;
    }

    @UserCouponBasicCacheRefresh
    public UserCouponBasic refreshUserCouponBasicCache(Long userCouponId) {
        return getBasic(userCouponId);
    }

    private UserCoupon findExist(Long userCouponId) {
        return userCouponRepository.findById(userCouponId).orElseThrow(() -> new NotFoundException(USER_COUPON));
    }

}
