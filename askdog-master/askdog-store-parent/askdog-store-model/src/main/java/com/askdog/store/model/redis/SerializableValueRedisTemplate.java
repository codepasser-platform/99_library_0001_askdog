package com.askdog.store.model.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Lazy
@Component
public class SerializableValueRedisTemplate<V extends Serializable> extends AbstractRedisTemplate<String, V> {

    @Autowired
    public SerializableValueRedisTemplate(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    void initDefaultKeySerializers() {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        setKeySerializer(stringSerializer);
        setHashKeySerializer(stringSerializer);
    }
}
