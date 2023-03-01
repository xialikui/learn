package com.example.sharding_jdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableAsync
@MapperScan(basePackages = {"com.example.sharding_jdbc.modules.**.mapper"})
public class ShardingJdbcApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class, args);
        System.out.println("启动结束");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShardingJdbcApplication.class);
    }
}
