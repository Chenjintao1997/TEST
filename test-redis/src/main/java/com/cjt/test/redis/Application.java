package com.cjt.test.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @Author: chenjintao
 * @Date: 2021/3/20 17:30
 */
@SpringBootApplication
@EnableRedisRepositories
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
