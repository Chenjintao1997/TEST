package com.cjt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.swagger.annotation.EnableSwaggerDocumentation;


@SpringBootApplication
@EnableSwaggerDocumentation
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
