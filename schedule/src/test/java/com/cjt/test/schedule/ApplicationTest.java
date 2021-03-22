package com.cjt.test.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chenjintao
 * @Date: 2019-12-05 14:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    private Scheduler scheduler;
    @Test
    public void testDeleteJob() throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey("job7","ccos_job"));
    }
}
