package com.cjt.test.schedule;

import com.cjt.test.job.JobTest;
import com.cjt.test.job.JobTestA;
import com.cjt.test.job.JobTestArg1;
import com.cjt.test.job.JobTestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.text.ParseException;

/**
 * @Author: chenjintao
 * @Date: 2019-08-30 12:15
 */
@RunWith(JUnit4.class)
public class ScheduleTest {

    @Test
    public void test1() throws ParseException, SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class).withIdentity("testJob1", "module1").build();

        //JobDetail jobDetail1 = JobBuilder.newJob(()->test2()).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger1", "module1").startNow().
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)/*.withMisfireHandlingInstructionNowWithExistingCount()*/).  //没测出来这个失火策略配上和不配有什么区别,因为默认在quartz.properties中配置超时时间为60s才放弃
                build();

//        CronTriggerImpl trigger1 = new CronTriggerImpl();
//        trigger1.setCronExpression("");
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        Thread.sleep(20000);
        scheduler.start();
        Thread.sleep(20000);
    }

    @Test
    public void test2() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(JobTestA.class).withIdentity("testJob2", "module2").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger2", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        Thread.sleep(1000);
        scheduler.start();
        Thread.sleep(1000);

    }


    @Test
    public void testSchedulerFactory() throws SchedulerException, InterruptedException {
        JobDetail jobDetailA = JobBuilder.newJob(JobTestA.class).withIdentity("testJobA", "module2").build();
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class).withIdentity("testJob", "module2").build();
        Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("testTriggerA", "module2").
                startNow().forJob(jobDetailA).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler schedulerA = schedulerFactory.getScheduler();
        schedulerA.scheduleJob(jobDetailA, triggerA);

        Thread.sleep(1000);
        schedulerA.start();
        schedulerA.scheduleJob(jobDetail, trigger);
        //schedulerA.start();
        Thread.sleep(1000);

    }

    @Test
    public void test3() throws SchedulerException, InterruptedException {
        JobDetail jobDetailA = JobBuilder.newJob(JobTestA.class).withIdentity("testJobA", "module2").build();
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class).withIdentity("testJob", "module2").build();
        Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("testTriggerA", "module2").
                startNow().forJob(jobDetailA).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler schedulerA = schedulerFactory.getScheduler();
        schedulerA.scheduleJob(jobDetailA, triggerA);

        Thread.sleep(2000);
        schedulerA.start();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
        //schedulerA.start();
        Thread.sleep(2000);
    }

    @Test
    public void test4() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class).withIdentity("testJob2", "module2").build();
        Trigger trigger = TriggerBuilder.
                newTrigger().
                withIdentity("testTrigger2", "module2").
                withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 1 ? *")).forJob(jobDetail).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        Thread.sleep(10000);
        scheduler.start();
        Thread.sleep(100000);
    }

    @Test
    public void testNullStart() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        Scheduler scheduler1 = schedulerFactory.getScheduler();
        scheduler.start();
    }

    @Test
    public void testDeleteJob() throws SchedulerException, InterruptedException {
        JobDetail jobDetailA = JobBuilder.newJob(JobTestA.class).withIdentity("testJobA", "module2").build();
        JobDetail jobDetail = JobBuilder.newJob(JobTest.class).withIdentity("testJob", "module2").build();
        Trigger triggerA = TriggerBuilder.newTrigger().withIdentity("testTriggerA", "module2").
                startNow().forJob(jobDetailA).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler schedulerA = schedulerFactory.getScheduler();

        schedulerA.start();
        Thread.sleep(2000);
        schedulerA.scheduleJob(jobDetailA, triggerA);

        schedulerA.scheduleJob(jobDetail, trigger);

        Thread.sleep(2000);
        schedulerA.deleteJob(jobDetailA.getKey());
        Thread.sleep(2000);
    }

    @Test
    public void testShutdown() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        scheduler.shutdown();
    }

    @Test
    public void testHasArgsExecutor() throws InterruptedException, SchedulerException {
//        testHasArgsExecutorMethod();
        testHasArgsExecutorMethod1();

        Thread.sleep(10000);
    }


    private void testHasArgsExecutorMethod() throws SchedulerException, InterruptedException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();


        scheduler.start();
        String executorStr = "测试内部类并执行带参数方法的定时任务";

        //JobTestArg jobTestArg = new JobTestArg();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("data", executorStr);
        JobDetail jobDetail = JobBuilder.newJob(JobTestArg1.class).withIdentity("testJob2", "module2").setJobData(jobDataMap).
                build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger2", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();

        scheduler.scheduleJob(jobDetail, trigger);
    }


    private void testExecutor(String str) {
        System.out.println("-------------------->测试带参数的方法" + str);
    }


    private void testHasArgsExecutorMethod1() throws SchedulerException, InterruptedException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();


        scheduler.start();
        String executorStr = "测试内部类并执行带参数方法的定时任务";

        //JobTestArg jobTestArg = new JobTestArg();


        class JobTestArg2 implements Job {   //应该是因为方法内部类的生命周期随着方法出栈就结束了导致的该任务进行时无法初始化该类，或者就是因为方法内部类并不会在方法区有记录，所以不能被放射初始化

            @Override
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//                testExecutor(executorStr);
                JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
                String data = jobDataMap.getString("data");
                testExecutor(data);
            }

            private void testExecutor(String str) {
                System.out.println("-------------------->测试带参数的方法" + str);
            }


        }


        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("data", executorStr);
        JobDetail jobDetail = JobBuilder.newJob(JobTestArg3.class).withIdentity("testJob2", "module2").setJobData(jobDataMap).
                build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger2", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();

        scheduler.scheduleJob(jobDetail, trigger);
    }


    public final class JobTestArg3 implements Job {   //因为不是public 所以不能被其他类newInstance 所以一直报错

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//                testExecutor(executorStr);

            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String data = jobDataMap.getString("data");
            testExecutor(data);
        }

        private void testExecutor(String str) {
            System.out.println("-------------------->测试带参数的方法" + str);
        }


    }

    @Test
    public void testJob() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
    }

    @Test
    public void testJob1() {
        JobFactory jobFactory = new SpringBeanJobFactory();
    }

    @Test
    public void testException() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(JobTestException.class).withIdentity("testJob2", "module2").
                build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger2", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        SchedulerFactory schedulerFactory1 = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        Scheduler scheduler1 = schedulerFactory.getScheduler();

        Scheduler scheduler2 = schedulerFactory1.getScheduler();
        ((StdSchedulerFactory) schedulerFactory).initialize();
        scheduler.scheduleJob(jobDetail, trigger);

        Thread.sleep(3000);
        scheduler.start();
        Thread.sleep(3000);
    }

    @Test
    public void testAutoWired() throws SchedulerException, InterruptedException {
        JobDetail jobDetail = JobBuilder.newJob(JobTestA.class).withIdentity("testJob2", "module2").
                build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testTrigger2", "module2").
                startNow().forJob(jobDetail).
                withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)).
                build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(); //如果这里用的SchedulerFactoryBean 可以用其方法factory.setJobFactory(new SpringBeanJobFactory()); 这样就不需要在scheduler内在set了，
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.setJobFactory(new SpringBeanJobFactory());

        Thread.sleep(3000);
        scheduler.start();
        Thread.sleep(3000);
    }


}


class JobTestArg implements Job {   //因为不是public 所以不能被其他类newInstance 所以一直报错

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//                testExecutor(executorStr);
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String data = jobDataMap.getString("data");
        testExecutor(data);
    }

    private void testExecutor(String str) {
        System.out.println("-------------------->测试带参数的方法" + str);
    }


}
