package com.cjt.test.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;

/**
 * @Author: chenjintao
 * @Date: 2021/3/22 23:05
 */
@Configuration
public class RedisConfig {

    @Bean(name = "defaultRedisTemplate")
    public RedisTemplate<Object, Object> defaultRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    /**
     * 设置redisTemplate的序列化方式，默认使用为jdk的序列化方式，stringRedisTemplate的默认方式为string序列化方式
     */
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置key的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        //设置value的序列化方式
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }
}
