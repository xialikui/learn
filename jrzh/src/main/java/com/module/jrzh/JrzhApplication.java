package com.module.jrzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan(basePackages = {"com.module.jrzh.**.dao"})
public class JrzhApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JrzhApplication.class, args);
        System.out.println("启动结束");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JrzhApplication.class);
    }

}
