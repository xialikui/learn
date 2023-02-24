package com.example.modules;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.example"})
@MapperScan(basePackages = {"com.example.modules.**.dao"})
//@EnableSwagger2
public class DesignApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DesignApplication.class, args);
        //初始化数据库连接池
        System.out.println("启动结束");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DesignApplication.class);
    }
}
