package com.cjt.ruleConfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenjintao
 * @Date: 2021/4/12 16:06
 */
@Configuration //好像不起作用，
public class RuleConfig {
    @Bean
    public IRule rule(){
        return new RandomRule();
    }
}
