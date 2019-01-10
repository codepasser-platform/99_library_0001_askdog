package com.askdog.store.service.impl.configuragtion;

import com.askdog.store.model.redis.SerializableValueRedisTemplate;
import com.askdog.store.service.impl.cache.hibernate.redis.HibernateL2CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    private SerializableValueRedisTemplate<Serializable> redisTemplate;

    @PostConstruct
    private void init() {
        HibernateL2CacheTemplate.redisTemplate(redisTemplate);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setExpires(boxingExpires(expires()));
        return redisCacheManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "askdog.cache.expires")
    public Map<String, Object> expires() {
        return new HashMap<>();
    }

    private Map<String, Long> boxingExpires(Map<String, Object> expires) {
        Map<String, Long> boxedExpires = new HashMap<>();
        expires.forEach((cacheName, expire) -> boxedExpires.put(cacheName, parseLong(valueOf(expire))));
        return boxedExpires;
    }

}