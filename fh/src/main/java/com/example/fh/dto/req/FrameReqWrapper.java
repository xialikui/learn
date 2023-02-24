package com.example.fh.dto.req;



import com.example.fh.enums.ServiceCodeEnum;
import lombok.Data;
import lombok.ToString;

/**
 * 包装类
 * 
 * @author weijiayu
 * @date 2022/5/16 10:10
 */
@Data
//@ToString
public class FrameReqWrapper {

    private ServiceCodeEnum wrapCodeEnum;
    private FrameReq reqDto;
    private String originalFrame;

}
