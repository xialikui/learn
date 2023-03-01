package com.example.sharding_jdbc.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author zhangboqing
 * @Date 2020/4/25
 */
@Configuration
@EnableTransactionManagement
public class ShardingsphereTransactionConfig {

    @Bean
    @Autowired
    public PlatformTransactionManager shardingsphereTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
