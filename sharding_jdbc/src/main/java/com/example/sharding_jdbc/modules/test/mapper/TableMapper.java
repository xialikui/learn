package com.example.sharding_jdbc.modules.test.mapper;


import com.example.sharding_jdbc.modules.test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TableMapper {
    int insertTable(User user);

    int deleteTableById(Long id);

    int updateTableById(@Param("id") Long id, @Param("name") String name);

    List<User> listAllTable();

}
