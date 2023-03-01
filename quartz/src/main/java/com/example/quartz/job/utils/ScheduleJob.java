package com.example.quartz.job.utils;

import com.example.quartz.job.entity.ScheduleJobEntity;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * @description: 任务调度JOB
 * @author: baopei
 * @date: 2018-03-21 11:44:53
 * @version: 1.0
 */
public class ScheduleJob extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(ScheduleJob.class);

    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //根据主键获取job对象
        ScheduleJobEntity jobEntity = (ScheduleJobEntity) context.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAMS_KEY);

        //任务开始时间
        long startTime = System.currentTimeMillis();

        ScheduleRunnable schedule = new ScheduleRunnable(jobEntity);
        Future<?> future = service.submit(schedule);

        try {
            //通过获取线程结果来捕获异常
            future.get();
            //任务总计时间
            long times = System.currentTimeMillis() - startTime;
            log.debug("执行完成，耗时:" + times / 1000f + "s");
        } catch (InterruptedException e) {
            log.error("任务执行失败,失败原因:" + e.getMessage());
        } catch (ExecutionException e) {
            log.error("任务执行失败,失败原因:" + e.getMessage());
        }
    }

}