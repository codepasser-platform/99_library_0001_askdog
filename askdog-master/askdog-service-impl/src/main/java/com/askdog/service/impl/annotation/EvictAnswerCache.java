package com.askdog.service.impl.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Caching(evict = {
        @CacheEvict(value = "cache:cell:answer:detail:stateless", key = "#answerId"),
        @CacheEvict(value = "cache:cell:answer:detail", key = "#userId + ':' + #answerId + ':true'"),
        @CacheEvict(value = "cache:cell:answer:detail", key = "#userId + ':' + #answerId + ':false'"),
        @CacheEvict(value = "cache:cell:snapshot:answer:detail:stateless", key = "#answerId"),
        @CacheEvict(value = "cache:cell:snapshot:answer:detail", key = "#userId + ':' + #answerId + ':true'"),
        @CacheEvict(value = "cache:cell:snapshot:answer:detail", key = "#userId + ':' + #answerId + ':false'")
})
public @interface EvictAnswerCache {
}
