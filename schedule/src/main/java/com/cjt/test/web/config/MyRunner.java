package com.cjt.test.web.config;

import com.ccos.job.TruncateOcmResultJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: chenjintao
 * @Date: 2019-12-05 10:31
 */
@Component
public class MyRunner implements ApplicationRunner {
    protected Logger log = LoggerFactory.getLogger(MyRunner.class);
    @Autowired
    private TruncateOcmResultJob truncateOcmResultJob;

    @Autowired
    SchedulerConfig schedulerConfig;
    private static String TRIGGER_GROUP_NAME = "ccos_trriger";
    private static String JOB_GROUP_NAME = "ccos_job";

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Scheduler scheduler = schedulerConfig.scheduler();
        //每天0点清空ocm_result表
        TriggerKey triggerKey7 = TriggerKey.triggerKey("trigger7", TRIGGER_GROUP_NAME);
        CronTrigger trigger7 = (CronTrigger) scheduler.getTrigger(triggerKey7);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        if (null == trigger7) {
            //定义一个JobDetail,其中的定义Job类，是真正的执行逻辑所在
            JobDetail jobDetail7 = JobBuilder.newJob(truncateOcmResultJob.getClass()).withIdentity("job7", JOB_GROUP_NAME).build();
            //定义一个Trigger
            trigger7 = TriggerBuilder.newTrigger().withIdentity("trigger7", TRIGGER_GROUP_NAME)
                    .withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail7, trigger7);
            log.info("Quartz 创建了job:...:{}", jobDetail7.getKey());
        } else {

            //更新Trigger
            trigger7 = TriggerBuilder.newTrigger().withIdentity("trigger7", TRIGGER_GROUP_NAME)
                    .withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey7,trigger7);  //更新
            log.info("job已存在:{}", trigger7.getKey());
        }


        scheduler.start();
    }
}
