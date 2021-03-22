package com.cjt.test.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: chenjintao
 * @Date: 2019-09-05 15:33
 */
public class JobTestException implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("-------->测试excepiton");

        JobExecutionException jobExecutionException = new JobExecutionException(new Exception());
        jobExecutionException.setUnscheduleFiringTrigger(true);
        throw new RuntimeException("测试异常");
    }
}
