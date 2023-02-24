package com.example.fh.server.codec;



import com.example.fh.dto.req.FrameReqWrapper;
import io.netty.buffer.ByteBuf;

/**
 * @author weijiayu
 * @date 2022/7/22 9:10
 */
public interface FrameEncoder {

    ByteBuf encodeRsp(FrameReqWrapper frameReqWrapper);
}
