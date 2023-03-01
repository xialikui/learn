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

package com.example.quartz.job.controller;


import com.example.quartz.job.common.PageUtils;
import com.example.quartz.job.common.R;
import com.example.quartz.job.common.ValidatorUtils;
import com.example.quartz.job.entity.ScheduleJobEntity;
import com.example.quartz.job.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 定时任务
 *
 * @author ChangSir
 * @date 2019-02-13 14:55:41
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService jobService;

    /**
     * 定时任务列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = jobService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 定时任务信息
     */
    @PostMapping("/info/{jobId}")
    public R info(@PathVariable("jobId") String jobId) {
        ScheduleJobEntity schedule = jobService.getById(jobId);

        return R.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @RequestMapping("/save")
    @PostMapping("保存定时任务")
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);
        jobService.save(scheduleJob);
        return R.ok();
    }

    /**
     * 修改定时任务
     */
    @RequestMapping("/update")
    @PostMapping("修改定时任务")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        jobService.update(scheduleJob);

        return R.ok();
    }

    /**
     * 删除定时任务
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] jobIds) {
        jobService.deleteBatch(jobIds);
        return R.ok();
    }

    /**
     * 立即执行任务
     */
    @RequestMapping("/run")
    public R run(@RequestBody String[] jobIds) {
        boolean r = jobService.run(jobIds);
        if (r) {
            return R.ok();
        }

        return R.error("任务执行失败");
    }

    /**
     * 暂停定时任务
     */
    @RequestMapping("/pause")
    public R pause(@RequestBody String[] jobIds) {
        jobService.pause(jobIds);

        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping("/resume")
    public R resume(@RequestBody String[] jobIds) {
        jobService.resume(jobIds);

        return R.ok();
    }

}
