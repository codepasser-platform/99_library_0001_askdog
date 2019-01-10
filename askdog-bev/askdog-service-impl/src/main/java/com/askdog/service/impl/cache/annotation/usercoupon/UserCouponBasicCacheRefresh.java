package com.askdog.service.impl.cache.annotation.usercoupon;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

import static com.askdog.service.impl.cache.annotation.usercoupon.UserCouponBasicCache.KEY;
import static com.askdog.service.impl.cache.annotation.usercoupon.UserCouponBasicCache.VALUE;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Caching(put = @CachePut(key = KEY, value = VALUE))
public @interface UserCouponBasicCacheRefresh {
}
