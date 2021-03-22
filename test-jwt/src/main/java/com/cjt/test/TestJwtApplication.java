package com.cjt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

/**
 * @Author: chenjintao
 * @Date: 2020/9/11 10:40
 */
@SpringBootApplication
@EnableSwaggerDocumentation
public class TestJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestJwtApplication.class);
    }
}
