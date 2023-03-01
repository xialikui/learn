package com.example.sharding_jdbc.modules.test.service.impl;

import com.example.sharding_jdbc.modules.test.entity.Order;
import com.example.sharding_jdbc.modules.test.mapper.OrderMapper;
import com.example.sharding_jdbc.modules.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public List<Order> getOrderByIds(List<Long> orderIds) {
        return orderMapper.findOrderByIds(orderIds);
    }
}

