package com.example.fh.modules.flood.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (CollectFloodControlData)实体类
 *
 * @author makejava
 * @since 2023-02-22 17:25:31
 */
@Data
public class CollectFloodControlData implements Serializable {
    private static final long serialVersionUID = -40032593632980659L;

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

