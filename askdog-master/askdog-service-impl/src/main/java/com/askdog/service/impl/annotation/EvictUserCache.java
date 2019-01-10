package com.askdog.service.impl.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Caching(evict = {
        @CacheEvict(value = "cache:cell:user:detail", key = "#userId"),
})
public @interface EvictUserCache {
}
