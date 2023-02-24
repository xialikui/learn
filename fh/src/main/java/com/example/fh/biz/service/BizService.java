package com.example.fh.biz.service;


import com.example.fh.dto.req.FrameReqWrapper;

/**
 * 业务处理接口
 * 
 * @author weijiayu
 * @date 2022/7/19 17:04
 */
public interface BizService {

    boolean handler(FrameReqWrapper frameReqWrapper);
}
