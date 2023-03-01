package com.example.sharding_jdbc.modules.test.service;

import com.example.sharding_jdbc.modules.test.entity.Order;

import java.util.List;

public interface OrderService {

    int insertOrder(Order order);

    List<Order> getOrderByIds(List<Long> orderIds);
}
