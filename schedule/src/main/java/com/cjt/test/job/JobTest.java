package com.cjt.test.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: chenjintao
 * @Date: 2019-08-30 10:59
 */
public class JobTest implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("888888888888testJobTest:--->"+jobExecutionContext.toString());
    }
}
