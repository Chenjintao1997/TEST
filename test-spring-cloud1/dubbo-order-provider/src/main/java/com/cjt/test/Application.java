package com.cjt.test;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springboard.swagger.annotation.EnableSwaggerDocumentation;


@SpringBootApplication
@EnableSwaggerDocumentation
@EnableDubbo
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
