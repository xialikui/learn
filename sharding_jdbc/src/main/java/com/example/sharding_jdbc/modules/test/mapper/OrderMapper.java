package com.example.sharding_jdbc.modules.test.mapper;

import com.example.sharding_jdbc.modules.test.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insertOrder(Order order);

    List<Order> findOrderByIds(List<Long> orderIds);
}
