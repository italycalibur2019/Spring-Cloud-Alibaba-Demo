package com.italycalibur.ciallo.common.serializer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.ObjectUtils;

/**
 * @description: fastjson2 redis 序列化类
 * @author dhr
 * @date 2025-02-13 13:41:10
 * @version 1.0
 */
@Slf4j
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    static final Filter AUTO_TYPE_FILTER = JSONReader.autoTypeFilter("com.italycalibur.ciallo");// 按需加上需要支持自动类型的类名前缀，范围越小越安全

    private final Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T value) throws SerializationException {
        if (value == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(value, JSONWriter.Feature.WriteClassName);
        } catch (Exception e) {
            log.error("Fastjson2 序列化错误：{}", e.getMessage());
            throw new SerializationException("无法序列化: " + e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ObjectUtils.isEmpty(bytes)) {
            return null;
        }
        try {
            return JSON.parseObject(bytes, clazz, AUTO_TYPE_FILTER);
        } catch (Exception e) {
            log.error("Fastjson2 反序列化错误：{}", e.getMessage());
            throw new SerializationException("无法反序列化: " + e.getMessage(), e);
        }
    }
}
