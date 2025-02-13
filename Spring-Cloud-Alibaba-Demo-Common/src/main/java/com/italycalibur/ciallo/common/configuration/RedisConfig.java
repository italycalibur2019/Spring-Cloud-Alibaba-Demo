package com.italycalibur.ciallo.common.configuration;

import com.italycalibur.ciallo.common.serializer.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 16:30:38
 * @description: redis 配置
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // 设置key序列化方式string
        redisTemplate.setKeySerializer(RedisSerializer.string()); // RedisSerializer.string() 等价于 new StringRedisSerializer()

        // 设置value的序列化方式json
        redisTemplate.setValueSerializer(redisSerializer());

        // 设置hash的key的序列化方式string
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // 设置hash的value的序列化方式json
        redisTemplate.setHashValueSerializer(redisSerializer());

        // 使配置生效
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public RedisSerializer<Object> redisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }
}
