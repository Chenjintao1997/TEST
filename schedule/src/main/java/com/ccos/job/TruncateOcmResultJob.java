package com.ccos.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @Author: chenjintao
 * @Date: 2019-12-04 16:32
 */
@Component
public class TruncateOcmResultJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //resultSV.truncate();
        //resultSV.getByResultId(null,null);
        System.out.println("-------------->定时任务成功执行");
    }
}
