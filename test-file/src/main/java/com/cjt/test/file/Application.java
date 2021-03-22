package com.cjt.test.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springboard.swagger.annotation.EnableSwaggerDocumentation;

/**
 * @Author: chenjintao
 * @Date: 2019-07-04 12:23
 */
@SpringBootApplication
@EnableSwaggerDocumentation
@ComponentScan("com.cjt.test.file.web")
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
