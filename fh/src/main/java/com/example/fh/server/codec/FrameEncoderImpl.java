package com.example.fh.server.codec;

import com.example.fh.constant.FrameConstant;
import com.example.fh.dto.req.FrameReqHeader;
import com.example.fh.dto.req.FrameReqWrapper;
import com.example.fh.util.Crc16Util;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;



import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/22 9:13
 */
@Service
@Slf4j
public class FrameEncoderImpl implements FrameEncoder {

    @Override
    public ByteBuf encodeRsp(FrameReqWrapper frameReqWrapper) {
        if (frameReqWrapper.getReqDto().getHeader().isM3Mode()) {
            return encodeM3Rsp(frameReqWrapper);
        } else {
            return encodeM24Rsp(frameReqWrapper);
        }
    }

    private ByteBuf encodeM3Rsp(FrameReqWrapper frameReqWrapper) {
        // TODO
        return null;
    }

    private ByteBuf encodeM24Rsp(FrameReqWrapper frameReqWrapper) {
        try {
            FrameReqHeader header = frameReqWrapper.getReqDto().getHeader();
            StringBuilder sb = new StringBuilder();
            sb.append(FrameConstant.HEADER_START_HEX).append(header.getRemoteStationAddr())
                .append(header.getCenterStationAddr()).append(header.getPwd()).append(header.getFuncCode())
                .append(FrameConstant.HEADER_RSP_DOWN_SYMBOL_AND_ZERO_LEN_HEX)
                .append(FrameConstant.HEADER_RSP_BODY_END_STX_HEX).append(FrameConstant.HEADER_RSP_BODY_END_EOT_HEX);
            sb.append(Crc16Util.crc16(Hex.decodeHex(sb.toString()), false).toUpperCase());
            return Unpooled.copiedBuffer(sb.toString().getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
