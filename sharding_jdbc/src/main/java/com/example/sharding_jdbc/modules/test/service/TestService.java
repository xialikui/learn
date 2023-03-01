package com.example.sharding_jdbc.modules.test.service;

import com.example.sharding_jdbc.modules.test.entity.User;
import com.example.sharding_jdbc.modules.test.mapper.TableMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TableMapper tableMapper;

    public Object table() {
        // 插入测试
        for (long i = 1; i <= 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("Name_" + i);
            user.setPhone("phone");
            user.setEmail("email");

            tableMapper.insertTable(user);
        }

        // 修改测试(模拟修改Id为1的数据)
        tableMapper.updateTableById(1L, "修改名称");

        // 删除测试(模拟修改Id为2的数据)
        tableMapper.deleteTableById(2L);

        // 查询测试
        List<User> list = tableMapper.listAllTable();

        return list;
    }
}
