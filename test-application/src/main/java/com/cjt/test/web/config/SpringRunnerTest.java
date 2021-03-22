package com.cjt.test.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Author: chenjintao
 * @Date: 2019-10-15 18:16
 */
@Component
public class SpringRunnerTest implements ApplicationRunner {

    @Autowired
    private SchedulerConfig config;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(config.getStr1());
        System.out.println(config.getStr2());
        Environment environment = config.getEnv();
        System.out.println(environment.getActiveProfiles());
        System.out.println(environment.getProperty("spring.profiles.active"));
        System.out.println(environment.getProperty("profiles.active"));
        System.out.println(System.getProperty("spring.profiles.active"));
        System.out.println(System.getProperty("profiles.active"));
        System.out.println(System.getProperties());
    }
}
