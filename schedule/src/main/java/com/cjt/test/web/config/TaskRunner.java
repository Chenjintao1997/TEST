package com.cjt.test.web.config;


import com.cjt.test.util.schedule.GuestTask;
import com.cjt.test.util.schedule.Task;
import com.cjt.test.util.schedule.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: chenjintao
 * @Date: 2019/9/9 10:18
 */
@Component
public class TaskRunner implements ApplicationRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);


    @Autowired
    private TaskManager taskManager;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LOGGER.info("--------->注册定时任务开始");
        long beginTime = System.currentTimeMillis();
        Task task = new GuestTask();
        //task.setCronExp("0 36 * * * ?");
        task.setCronExp("0/30 * * * * ?");
        taskManager.addTask("-1", task);
        long endTime = System.currentTimeMillis();
        LOGGER.info("--------->注册定时任务结束，耗时：" + (endTime - beginTime) + "mm");
    }
}
