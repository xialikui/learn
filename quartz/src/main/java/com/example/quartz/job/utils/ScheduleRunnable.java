package com.example.quartz.job.utils;


import com.example.quartz.job.common.SpringContextUtils;
import com.example.quartz.job.entity.ScheduleJobEntity;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description: 任务调度执行线程
 * @author: baopei
 * @date: 2018-03-21 11:49:09
 * @version: 1.0
 */
public class ScheduleRunnable implements Runnable {

    private ScheduleJobEntity jobEntity;

    public ScheduleRunnable(ScheduleJobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }

    @Override
    public void run() {
        //利用反射执行bean中的方法
        try {
            Object target = SpringContextUtils.getBean(jobEntity.getBeanName());
            Object params = jobEntity.getParams();
            Method method = null;
            if (null != params && !params.equals("")) {
                method = target.getClass().getDeclaredMethod(jobEntity.getMethodName(), params.getClass());
                ReflectionUtils.makeAccessible(method);
                method.invoke(target, params);
            } else {
                method = target.getClass().getDeclaredMethod(jobEntity.getMethodName());
                ReflectionUtils.makeAccessible(method);
                method.invoke(target);
            }

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("执行任务调度异常,找不到执行方,详细:" + e.getMessage());
        } catch (SecurityException e) {
            throw new RuntimeException("执行任务调度异常,详细:" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("执行任务调度异常,详细:" + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("执行任务调度异常,详细:" + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("执行任务调度异常,详细:" + e.getMessage());
        }
    }

}