package com.cjt.test.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;

/**
 * @Author: chenjintao
 * @Date: 2019-08-30 09:47
 */
public class schedule {



    public void test() throws ParseException, SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class).withIdentity("testJob1","module1").build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger1","module1").startNow().
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).
                build();

        CronTriggerImpl trigger1 = new CronTriggerImpl();
        trigger1.setCronExpression("");
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
    }

}
