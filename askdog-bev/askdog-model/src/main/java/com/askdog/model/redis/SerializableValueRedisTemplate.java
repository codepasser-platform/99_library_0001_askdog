package com.askdog.model.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Lazy
@Component
public class SerializableValueRedisTemplate<V extends Serializable> extends AbstractRedisTemplate<Object, V> {

    @Autowired
    public SerializableValueRedisTemplate(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    void initDefaultKeySerializers() {
        RedisSerializer<Object> jdkSerializer = new JdkSerializationRedisSerializer();
        setKeySerializer(jdkSerializer);
        setHashKeySerializer(jdkSerializer);
    }
}