package com.example.fh.modules.flood.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.example.fh.modules.flood.entity.CollectFloodControlData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (CollectFloodControlData)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-22 17:25:30
 */
public interface CollectFloodControlDataDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CollectFloodControlData queryById(String id);


    /**
     * 查询指定行数据
     *
     * @param params 查询条件
     * @param page   分页对象
     * @return 对象列表
     */
    List<CollectFloodControlData> getPageList(@Param("page") IPage<CollectFloodControlData> page, @Param("params") Map<String, Object> params);

    /**
     * 统计总行数
     *
     * @param collectFloodControlData 查询条件
     * @return 总行数
     */
    long count(CollectFloodControlData collectFloodControlData);

    /**
     * 新增数据
     *
     * @param collectFloodControlData 实例对象
     * @return 影响行数
     */
    int insert(CollectFloodControlData collectFloodControlData);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CollectFloodControlData> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CollectFloodControlData> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CollectFloodControlData> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CollectFloodControlData> entities);

    /**
     * 修改数据
     *
     * @param collectFloodControlData 实例对象
     * @return 影响行数
     */
    int update(CollectFloodControlData collectFloodControlData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

