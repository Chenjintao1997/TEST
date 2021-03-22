package com.cjt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

/**
 * @Author: chenjintao
 * @Date: 2019-10-15 18:02
 */


@SpringBootApplication
@EnableSwaggerDocumentation
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
