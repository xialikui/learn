package com.example.sharding_jdbc.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author zhangboqing
 * @Date 2020/4/23
 */
@Configuration
@MapperScan(basePackages = "com.example.sharding_jdbc.modules.**.mapper", sqlSessionFactoryRef = "sqlSessionFactoryForShardingjdbc")
public class ShardingsphereMybatisConfig {

    @Autowired
    @Qualifier("shardingDataSource")
    private DataSource dataSource;

    @Bean("sqlSessionFactoryForShardingjdbc")
    public SqlSessionFactory sqlSessionFactoryForShardingjdbc() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:mapper/**/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.example.sharding_jdbc.modules.**.entity");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }

}
