package com.cjt.test;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.mybatis.annotation.EnableMyBatisPersistence;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

/**
 * @Author: chenjintao
 * @Date: 2019-10-15 18:02
 */


@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableSwaggerDocumentation
@EnableMyBatisPersistence({
        "com.cjt.test"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
