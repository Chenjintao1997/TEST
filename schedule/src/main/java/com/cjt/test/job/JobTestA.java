package com.cjt.test.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: chenjintao
 * @Date: 2019-08-30 16:55
 */
public class JobTestA implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("AAA:testJobTest:--->"+jobExecutionContext.toString());
    }
}
