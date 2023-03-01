package com.example.sharding_jdbc.modules.test.controller;

import com.example.sharding_jdbc.common.R;
import com.example.sharding_jdbc.modules.test.entity.Order;
import com.example.sharding_jdbc.modules.test.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/addOrder")
    @ResponseBody
    public R addOrder(@Valid @RequestBody Order order, @NotNull BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return R.error(message);
        }
        int i = orderService.insertOrder(order);
        if (i > 0) {
            return R.ok();
        }
        return R.error("失败");
    }

    @PostMapping(path = "/getOrder")
    @ResponseBody
    public R getOrder(@NotNull @RequestBody List<Long> orderIds) {
        if (orderIds.isEmpty()) {
            return R.error("参数orderIds不能为空");
        }
        List<Order> orderList = orderService.getOrderByIds(orderIds);
        if (orderList.isEmpty()) {
            return R.error("没有查询到你想要的数据");
        }
        return R.ok().put("data", orderList);
    }
}

