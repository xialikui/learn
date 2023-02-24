package com.example.fh.biz.service.impl;

import com.example.fh.biz.service.BizService;
import com.example.fh.dto.req.FrameReqWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * @author weijiayu
 * @date 2022/7/19 17:06
 */
@Service
@Slf4j
public class BizServiceImpl implements BizService {

    @Override
    public boolean handler(FrameReqWrapper frameReqWrapper) {
        return false;
    }
}
