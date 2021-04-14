package com.cjt.test.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenjintao
 * @Date: 2021/4/12 19:52
 */
@Configuration
public class FeignConfig {
    @Bean
    /**
     * 日志增强配置
     * 设置feign日志级别
     * NONE，无记录（DEFAULT），默认的
     * BASIC，只记录请求方法和URL以及响应状态代码和执行时间
     * HEADERS，记录基本信息以及请求和响应标头
     * FULL，记录请求和响应的头文件，正文和元数据。
     * 还需要在yml中配置哪个service开启日志
     */
    Logger.Level level() {
        return Logger.Level.FULL;
    }
}
