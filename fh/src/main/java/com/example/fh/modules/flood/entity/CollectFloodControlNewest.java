package com.example.fh.modules.flood.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (CollectFloodControlNewest)实体类
 *
 * @author makejava
 * @since 2023-02-22 17:25:35
 */
@Data
public class CollectFloodControlNewest implements Serializable {
    private static final long serialVersionUID = 943503825263338620L;

    private String id;
    /**
     * 点位编号
     */
    private String pointMn;
    /**
     * 因子编号
     */
    private String monitorCode;
    /**
     * 因子时间
     */
    private String monitorTime;
    /**
     * 因子数据
     */
    private Double monitorData;
    /**
     * 标识
     */
    private String flag;

    private Date createTime;
    /**
     * 发送时间
     */
    private String sendTime;




}

