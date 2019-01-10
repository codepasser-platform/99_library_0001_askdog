package com.askdog.service.impl.cache.annotation.usercoupon;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

import static com.askdog.service.impl.cache.annotation.usercoupon.UserCouponBasicCache.KEY;
import static com.askdog.service.impl.cache.annotation.usercoupon.UserCouponBasicCache.VALUE;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Caching(cacheable = @Cacheable(value = VALUE,key = KEY))
public @interface UserCouponBasicCache {
    String KEY = "#userCouponId";
    String VALUE = "cache:service:userCoupon:basic";
}
