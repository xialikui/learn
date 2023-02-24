package com.example.fh.server.codec;


import com.example.fh.dto.req.FrameReqBody;
import com.example.fh.enums.FrameFuncCodeEnum;

/**
 * @author weijiayu
 * @date 2022/7/19 18:24
 */
public interface FrameBodyDecoder {

    FrameReqBody decodeM3Body(char[] bodyFrame);

    FrameReqBody decodeM124Body(char[] bodyFrame, FrameFuncCodeEnum funcEnum);
}
