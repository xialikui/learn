/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.quartz.job.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quartz.job.common.PageUtils;
import com.example.quartz.job.common.Query;
import com.example.quartz.job.dao.ScheduleJobDao;
import com.example.quartz.job.entity.ScheduleJobEntity;
import com.example.quartz.job.service.ScheduleJobService;
import com.example.quartz.job.utils.QuartzManager;
import com.example.quartz.job.utils.ScheduleRunnable;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChangSir
 * @descript 定时任务
 * @date 2019-02-19 09:45:03
 */
@Service
@ConfigurationProperties(prefix = "runjob")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {

    private static Logger log = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);

    @Resource(name = "schedulerSQL")
    private Scheduler scheduler;

    @Value("${runjob.run}")
    private Boolean run;

	/*@Autowired
	private ExcessWebSocketClient ewsc;*/

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {

        List<ScheduleJobEntity> jobList = this.list();
        String dir = System.getProperty("user.dir");
		/*ewsc.connect();
		ewsc.send("服务器重启");*/
        if (dir.contains("apache-tomcat-8.5.9-9082"))
            run = false;
        if (run) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ScheduleJobEntity e : jobList) {
                //创建调度任务
                QuartzManager.create(scheduler, e);
            }
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");

        IPage<ScheduleJobEntity> page = this.page(
                new Query<ScheduleJobEntity>(params).getPage(),
                new QueryWrapper<ScheduleJobEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtils(page);
    }

    /**
     * 插入任务
     *
     * @param scheduleJob
     * @return
     */
    @Transactional
    public boolean save(ScheduleJobEntity scheduleJob) {
        //1.插入任务
        baseMapper.insert(scheduleJob);
        //2.添加任务
        QuartzManager.create(scheduler, scheduleJob);

        return true;
    }

    /**
     * 更新数据
     *
     * @param scheduleJob
     */
    @Transactional
    public void update(ScheduleJobEntity scheduleJob) {
        //1.更新数据库
        baseMapper.updateById(scheduleJob);
        //2.更新定时任务
        QuartzManager.modify(scheduler, scheduleJob);
    }

    /**
     * 删除任务
     *
     * @param jobIds
     */
    @Transactional
    public void deleteBatch(String[] jobIds) {
        //1.删除定时任务
        List<ScheduleJobEntity> scheduleJobList = baseMapper.selectBatchIds(Arrays.asList(jobIds));
        for (ScheduleJobEntity scheduleJobEntity : scheduleJobList) {
            QuartzManager.delete(scheduler, scheduleJobEntity);
        }
        //2.删除数据库
        baseMapper.deleteBatchIds(Arrays.asList(jobIds));
    }

    /**
     * 立即执行任务
     *
     * @param jobIds
     */
    public boolean run(String[] jobIds) {
        try {
            List<ScheduleJobEntity> scheduleJobList = baseMapper.selectBatchIds(Arrays.asList(jobIds));
            for (ScheduleJobEntity scheduleJobEntity : scheduleJobList) {
                new Thread(new ScheduleRunnable(scheduleJobEntity)).run();
            }

            return true;
        } catch (Exception e) {
            log.error("执行任务失败：" + e.getMessage());

            return false;
        }
    }

    /**
     * 暂停任务
     *
     * @param jobIds
     */
    @Transactional
    public void pause(String[] jobIds) {
        executorSQL(jobIds, ScheduleJobEntity.SCHEDULE_JOB_STOP);

        //2.更新任务
        List<ScheduleJobEntity> scheduleJobList = baseMapper.selectBatchIds(Arrays.asList(jobIds));
        for (ScheduleJobEntity scheduleJobEntity : scheduleJobList) {
            QuartzManager.pause(scheduler, scheduleJobEntity);
        }
    }

    /**
     * 运行任务
     *
     * @param jobIds
     */
    @Transactional
    public void resume(String[] jobIds) {
        executorSQL(jobIds, ScheduleJobEntity.SCHEDULE_JOB_RUN);

        //2.更新任务
        List<ScheduleJobEntity> scheduleJobList = baseMapper.selectBatchIds(Arrays.asList(jobIds));
        for (ScheduleJobEntity scheduleJobEntity : scheduleJobList) {
            QuartzManager.resume(scheduler, scheduleJobEntity);
        }
    }

    /**
     * 执行状态
     *
     * @param jobIds
     * @param status
     */
    private void executorSQL(String[] jobIds, int status) {
        //1.更新数据库
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobId", jobIds);
        map.put("status", status);
        baseMapper.updateBatch(map);
    }

}
