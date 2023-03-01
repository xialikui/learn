package com.example.sharding_jdbc.common;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.*;

/**
 * @Author zhangboqing
 * @Date 2020/4/25
 */
@Configuration
@Slf4j
public class ShardingSphereDataSourceConfig {
    @Bean(name = "m1")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.m1")
    public DataSource m1() {
        return DataSourceBuilder.create().build();
    }


    @Bean("shardingDataSource")
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("m1");
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        //shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.getBroadcastTables().add("t_config");
        //shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "m1${user_id % 2}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(getShardingStrategyConfiguration());
        // ShardingPropertiesConstant相关配置选项
        Properties properties = new Properties();
        properties.put("sql.show", true);
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }

    private ShardingStrategyConfiguration getShardingStrategyConfiguration() {
        // 精确匹配
        PreciseShardingAlgorithm<Long> preciseShardingAlgorithm = new PreciseShardingAlgorithm<Long>() {
            @Override
            public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
                String prefix = shardingValue.getLogicTableName();
                Long orderId = shardingValue.getValue();
                long index = orderId % 2 == 1 ? 1 : 2;
                // t_order + "" + 0 = t_order0
                String tableName = prefix + "_" + index;
                // 精确查询、更新之类的，可以返回不存在表，进而给前端抛出异常和警告。
                if (availableTargetNames.contains(tableName) == false) {
                    //LogUtils.error(log,"PreciseSharding","orderId:{},不存在对应的数据库表{}！", orderId, tableName);
                    return availableTargetNames.iterator().next();
                }
                return tableName;
//                return availableTargetNames.iterator().next();
            }
        };
        // 范围匹配
        RangeShardingAlgorithm<Long> rangeShardingAlgorithm = new RangeShardingAlgorithm<Long>() {
            @Override
            public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
                String prefix = shardingValue.getLogicTableName();
                Collection<String> resList = new ArrayList<>();

                Range<Long> valueRange = shardingValue.getValueRange();
                if (!valueRange.hasLowerBound() || !valueRange.hasUpperBound()) {
                    return availableTargetNames;
                }
                long lower = shardingValue.getValueRange().lowerEndpoint();
                BoundType lowerBoundType = shardingValue.getValueRange().lowerBoundType();
                long upper = shardingValue.getValueRange().upperEndpoint();
                BoundType upperBoundType = shardingValue.getValueRange().upperBoundType();
                long startValue = lower;
                long endValue = upper;
                if (lowerBoundType.equals(BoundType.OPEN)) {
                    startValue++;
                }
                if (upperBoundType.equals(BoundType.OPEN)) {
                    endValue--;
                }

                for (long i = startValue; i <= endValue; i++) {
                    long index = i % 2;
                    String res = prefix + "" + index;
                    // 精确查询、更新之类的，可以返回不存在表，进而给前端抛出异常和警告。
                    if (availableTargetNames.contains(res) == false) {
                        //LogUtils.error(log,"RangeSharding","orderId:{},不存在对应的数据库表{}！", i, res);
                        resList.add(res);
                    }
                }
                if (resList.size() == 0) {
                    //LogUtils.error(log,"RangeSharding","无法获取对应表，因此将对全表进行查询！orderId范围为:{}到{}",startValue,endValue);
                    return availableTargetNames;
                }
                return resList;
            }
        };
        ShardingStrategyConfiguration strategyConf = new StandardShardingStrategyConfiguration("order_id", preciseShardingAlgorithm, rangeShardingAlgorithm);
        return strategyConf;
    }


    TableRuleConfiguration getOrderTableRuleConfiguration() {
        // 逻辑表 + 实际节点
        TableRuleConfiguration result = new TableRuleConfiguration("t_order", "m1.t_order_${1..2}");
        // 主键生成配置
        result.setKeyGeneratorConfig(getKeyGeneratorConfigurationForTOrder());
        return result;
    }

    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "m1.t_order_item${1..2}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfigurationForTOrderItem());
        return result;
    }

    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("m1", m1());
        /*  result.put("ds1", DataSourceUtils.createDataSource("ds1"));*/
        return result;
    }

    private KeyGeneratorConfiguration getKeyGeneratorConfigurationForTOrder() {
        Properties keyGeneratorProp = getKeyGeneratorProperties();
        return new KeyGeneratorConfiguration("SNOWFLAKE", "order_id", keyGeneratorProp);
    }

    private Properties getKeyGeneratorProperties() {
        Properties keyGeneratorProp = new Properties();
       /* String distributeProcessIdentify = NetUtils.getLocalAddress() + ":" + getProcessId();
        String workId = String.valueOf(convertString2Long(distributeProcessIdentify));
        keyGeneratorProp.setProperty("worker.id", workId);*/
        //LogUtils.info(log, "shardingsphere init", "shardingsphere work id raw string is {}, work id is {}", distributeProcessIdentify, workId);
        return keyGeneratorProp;
    }

    private KeyGeneratorConfiguration getKeyGeneratorConfigurationForTOrderItem() {
        Properties keyGeneratorProp = getKeyGeneratorProperties();
        return new KeyGeneratorConfiguration("SNOWFLAKE", "id", keyGeneratorProp);
    }

    private String getProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return pid;
    }

    private Long convertString2Long(String str) {
        long hashCode = str.hashCode() + System.currentTimeMillis();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode % (1L << 10);
    }


}
