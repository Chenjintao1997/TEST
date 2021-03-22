package com.cjt.test.web.config;

import com.cjt.test.util.schedule.TaskManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author: chenjintao
 * @Date: 2020-05-20 19:40
 */
@Configuration
public class TaskConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }

    @Bean
    public TaskManager taskManager() {
        return new TaskManager();
    }
}
