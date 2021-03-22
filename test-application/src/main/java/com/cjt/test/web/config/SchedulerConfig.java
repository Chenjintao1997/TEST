package com.cjt.test.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Author: chenjintao
 * @Date: 2019-10-15 18:08
 */
@Configuration
@ConditionalOnExpression("!'prod'.equals('${spring.profiles.active}')")
public class SchedulerConfig {
    //@Value("${profiles.active}")
    private String str1;

    @Autowired
    private Environment env;

    @Value("${spring.profiles.active}")
    private String str2;

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }
}
