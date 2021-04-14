package com.cjt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import springboard.swagger.annotation.EnableSwaggerDocumentation;


@SpringBootApplication
@EnableSwaggerDocumentation
@EnableEurekaClient //也可不填写
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
