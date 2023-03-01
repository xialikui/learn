package com.example.sharding_jdbc.modules.test.controller;


import com.example.sharding_jdbc.modules.test.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    // TODO DEMO配置文件中，存在多种配置策略，如何只需要对某一种类型进行测试，则需要修改配置文件配置
    @Resource
    private TestService testService;

    @GetMapping("/table")
    public Object table() {
        return testService.table();
    }


}