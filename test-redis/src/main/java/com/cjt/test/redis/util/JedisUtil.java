package com.cjt.test.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: chenjintao
 * @Date: 2021/3/17 23:33
 */
public class JedisUtil {
    private static final JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        jedisPool = new JedisPool(config, "cjtbc.com", 6379, 100, "aAa132132");
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
