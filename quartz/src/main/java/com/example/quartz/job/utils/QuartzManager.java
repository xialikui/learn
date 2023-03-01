package com.example.quartz.job.utils;

import com.example.quartz.job.entity.ScheduleJobEntity;
import org.quartz.*;

public class QuartzManager {

    /**
     * 创建任务调度
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void create(Scheduler scheduler, ScheduleJobEntity jobEntity) {

        try {
            //生成KEY
            JobKey jobkey = new JobKey(jobEntity.getJobKey());
            TriggerKey triggerKey = new TriggerKey(jobEntity.getJobKey());

            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(jobkey).build();
            //构建一个表达式,失效后再恢复并立即执行
            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //构建一个trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(schedBuilder).build();
            //放入对象
            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAMS_KEY, jobEntity);

            //注册触发器
            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (jobEntity.getStatus() == 1) {
                pause(scheduler, jobEntity);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改调度任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void modify(Scheduler scheduler, ScheduleJobEntity jobEntity) {
        TriggerKey triggerKey = new TriggerKey(jobEntity.getJobKey());

        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(schedBuilder).build();
            //放入对象
            trigger.getJobDataMap().put(ScheduleJobEntity.JOB_PARAMS_KEY, jobEntity);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (jobEntity.getStatus() == 1) {
                pause(scheduler, jobEntity);
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除调度任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void delete(Scheduler scheduler, ScheduleJobEntity jobEntity) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobEntity.getJobKey()));
            System.err.println("删除调度任务");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行调度任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity jobEntity) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleJobEntity.JOB_PARAMS_KEY, jobEntity);
            scheduler.triggerJob(new JobKey(jobEntity.getJobKey()), dataMap);
            System.err.println("运行调度任务");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停调度任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void pause(Scheduler scheduler, ScheduleJobEntity jobEntity) {
        try {
            scheduler.pauseJob(new JobKey(jobEntity.getJobKey()));
            System.err.println("暂停调度任务");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复调度任务
     *
     * @param scheduler
     * @param jobEntity
     */
    public static void resume(Scheduler scheduler, ScheduleJobEntity jobEntity) {
        try {
            scheduler.resumeJob(new JobKey(jobEntity.getJobKey()));
            System.err.println("恢复调度任务");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}