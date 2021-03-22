package com.cjt.test.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: chenjintao
 * @Date: 2019-09-04 22:06
 */
public class JobTestArg1 implements Job {

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
