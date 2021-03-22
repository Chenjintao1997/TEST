package com.cjt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

/**
 * @Author: chenjintao
 * @Date: 2019-12-05 10:04
 */

@SpringBootApplication
@EnableSwaggerDocumentation
@ComponentScan("com")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
