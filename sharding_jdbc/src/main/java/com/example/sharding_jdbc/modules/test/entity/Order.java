package com.example.sharding_jdbc.modules.test.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CREATE TABLE `t_order_1` (
 * `order_id` int(11) NOT NULL COMMENT '订单id',
 * `price` decimal(10,2) NOT NULL COMMENT '订单价格',
 * `user_id` int(11) NOT NULL COMMENT '下单用户id',
 * `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单状态',
 * PRIMARY KEY (`order_id`) USING BTREE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
 * <p>
 * <p>
 * CREATE TABLE `t_order_2` (
 * `order_id` int(11) NOT NULL COMMENT '订单id',
 * `price` decimal(10,2) NOT NULL COMMENT '订单价格',
 * `user_id` int(11) NOT NULL COMMENT '下单用户id',
 * `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单状态',
 * PRIMARY KEY (`order_id`) USING BTREE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
 */

@Data
public class Order implements Serializable {

    @NotNull(message = "参数orderId不能为空")
    private Long orderId;

    @NotNull(message = "参数price不能为空")
    private BigDecimal price;

    @NotNull(message = "参数userId不能为空")
    private Integer userId;

    @NotBlank(message = "参数status不能为空")
    private String status;
}

