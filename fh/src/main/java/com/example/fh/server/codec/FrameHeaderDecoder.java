package com.example.fh.server.codec;


import com.example.fh.dto.req.FrameReqHeader;

/**
 * @author weijiayu
 * @date 2022/7/19 18:27
 */
public interface FrameHeaderDecoder {

    FrameReqHeader decodeHeader(char[] frame);
}
