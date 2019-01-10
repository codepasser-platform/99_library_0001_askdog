package com.askdog.service.impl.annotation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Caching(evict = {
        @CacheEvict(value = "cache:cell:question:detail:stateless", key = "#questionId"),
        @CacheEvict(value = "cache:cell:question:detail", key = "#userId + ':' + #questionId + ':true'"),
        @CacheEvict(value = "cache:cell:question:detail", key = "#userId + ':' + #questionId + ':false'"),
        @CacheEvict(value = "cache:cell:snapshot:question:detail:stateless", key = "#questionId"),
        @CacheEvict(value = "cache:cell:snapshot:question:detail", key = "#userId + ':' + #questionId + ':true'"),
        @CacheEvict(value = "cache:cell:snapshot:question:detail", key = "#userId + ':' + #questionId + ':false'")
})
public @interface EvictQuestionCache {
}
