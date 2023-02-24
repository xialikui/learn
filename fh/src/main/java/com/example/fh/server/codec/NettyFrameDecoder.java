package com.example.fh.server.codec;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import com.example.fh.constant.FrameConstant;
import com.example.fh.dto.req.FrameReq;
import com.example.fh.dto.req.FrameReqBody;
import com.example.fh.dto.req.FrameReqHeader;
import com.example.fh.dto.req.FrameReqWrapper;
import com.example.fh.enums.FrameFuncCodeEnum;
import com.example.fh.enums.ServiceCodeEnum;
import com.example.fh.modules.flood.dao.CollectFloodControlDataDao;
import com.example.fh.modules.flood.dao.CollectFloodControlNewestDao;
import com.example.fh.modules.flood.entity.CollectFloodControlData;
import com.example.fh.modules.flood.entity.CollectFloodControlNewest;
import com.example.fh.util.DateUtil;
import com.example.fh.util.FrameUtil;
import com.example.fh.util.HexStringUtil;
import com.example.fh.util.SpringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author weijiayu
 * @date 2022/5/13 9:49
 */
@Slf4j
public class NettyFrameDecoder extends ByteToMessageDecoder {
    private boolean crcVerifyFlag = false;
    private FrameHeaderDecoder headerDecoder;
    private FrameBodyDecoder bodyDecoder;

    @Autowired
    private CollectFloodControlNewestDao collectFloodControlNewestDao;

    @Autowired
    private CollectFloodControlDataDao collectFloodControlDataDao;

    public NettyFrameDecoder() {
    }

    public NettyFrameDecoder(boolean crcVerifyFlag, FrameHeaderDecoder headerDecoder, FrameBodyDecoder bodyDecoder) {
        this.crcVerifyFlag = crcVerifyFlag;
        this.headerDecoder = headerDecoder;
        this.bodyDecoder = bodyDecoder;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {

        collectFloodControlNewestDao = SpringUtil.getBean("collectFloodControlNewestDao");
        collectFloodControlDataDao = SpringUtil.getBean("collectFloodControlDataDao");
        FrameReqWrapper wrapper = this.decodeFrame(byteBuf);
        list.add(wrapper);
    }

    public FrameReqWrapper decodeFrame(ByteBuf byteBuf) {
        FrameReqWrapper wrapper = new FrameReqWrapper();
        FrameReq frameReq = new FrameReq();
        wrapper.setReqDto(frameReq);
        try {
            String frameHexStr = ByteBufUtil.hexDump(byteBuf);
            log.debug("sl651-2014 frame hex content>>>" + frameHexStr);
            wrapper.setOriginalFrame(frameHexStr);
            char[] frame = HexStringUtil.hexStr2CharArray(frameHexStr);
            if (crcVerifyFlag && !FrameUtil.verifyCRC16Code(frame)) {
                log.warn("sl651-2014 frame crc verify fail>>>" + frameHexStr);
                wrapper.setWrapCodeEnum(ServiceCodeEnum.SERVICE_CRC_VERIFY_FAIL);
                return wrapper;
            }
            FrameReqHeader header = headerDecoder.decodeHeader(frame);
            frameReq.setHeader(header);
            int bodyLen = header.getBodyLen().intValue();
            if (header.isM3Mode()) {
                frameReq.setBody(bodyDecoder.decodeM3Body(FrameUtil.getM3Body(frame, bodyLen)));
            } else {
                frameReq.setBody(bodyDecoder.decodeM124Body(FrameUtil.getM124Body(frame, bodyLen),
                        FrameFuncCodeEnum.getFrameFuncEnum(header.getFuncCode())));
            }
            frameReq.setBodyEndSymbol(FrameUtil.getBodyEndSymbol(frame));
            frameReq.setCrcCode(FrameUtil.getCRC16Code(frame));
            wrapper.setWrapCodeEnum(ServiceCodeEnum.SERVICE_SUCCESS);
            log.debug("sl651-2014 frame decode content>>>" + frameReq.toString());
        } catch (Exception e) {
            log.error("sl651-2014 frame decode error", e);
            wrapper.setWrapCodeEnum(ServiceCodeEnum.SERVICE_ERROR);
        } finally {
            byteBuf.skipBytes(byteBuf.readableBytes());
        }
        log.error("sl651-2014 frame decode wrapper---------", JSONObject.toJSON(wrapper));
        //业务处理
        if (StringUtils.equals("0", wrapper.getWrapCodeEnum().getCode())) {
            if (null != wrapper && null != wrapper.getReqDto() && null != wrapper.getReqDto().getBody()) {
                //主体
                FrameReqBody frameReqBody = wrapper.getReqDto().getBody();
                if (CollectionUtils.isNotEmpty(frameReqBody.getElementMap())) {
                    List<CollectFloodControlNewest> newestList = new ArrayList<>();
                    List<CollectFloodControlData> floodControlList = new ArrayList<>();
                    frameReqBody.getElementMap().entrySet().forEach(m -> {
                        m.getValue().forEach(c -> {
                            CollectFloodControlNewest newest = new CollectFloodControlNewest();
                            newest.setId(IdWorker.get32UUID());
                            newest.setPointMn(frameReqBody.getRemoteStationAddr());
                            newest.setMonitorCode(c.getTypeCode());
                            newest.setMonitorTime(DateUtil.format(DateUtil.stringToDate(frameReqBody.getObserveTime(), DateUtil.FORMAT_YMD_HM), DateUtil.DATE_TIME_PATTERN));
                            newest.setMonitorData(c.getVal());
                            newest.setFlag("N");
                            newest.setCreateTime(new Date());
                            newest.setSendTime(DateUtil.format(DateUtil.stringToDate(frameReqBody.getSendTime(), DateUtil.FORMAT_YMD_HMS), DateUtil.DATE_TIME_PATTERN));
                            newestList.add(newest);
                            CollectFloodControlData collectFloodControlData = new CollectFloodControlData();
                            BeanUtils.copyProperties(newest, collectFloodControlData);
                            collectFloodControlData.setId(IdWorker.get32UUID());
                            floodControlList.add(collectFloodControlData);
                        });

                    });
                    //collectFloodControlNewestDao.insertOrUpdateBatch(newestList);
                    //collectFloodControlDataDao.insertBatch(floodControlList);
                }
            }

        }

        System.out.println(JSONObject.toJSON(wrapper));
        return wrapper;
    }

    public static void main(String[] args) throws Exception {
        String data =
                "7E 7E 00 00 00 00 00 22 00 00 32 00 2B 02 00 01 19 03 05 15 00 35 F1 F1 00 00 00 00 22 48 F0 F0 19 03 05 15 00 22 19 00 00 50 1A 19 00 00 50 20 19 00 00 50 26 19 00 00 50 03 8A 15".replaceAll(" ", "").toLowerCase();
        ;
        ByteBuf buffer = Unpooled.copiedBuffer(Hex.decodeHex(data));
        System.out.println(ByteBufUtil.hexDump(buffer));

        NettyFrameDecoder decoder =
                new NettyFrameDecoder(false, new FrameHeaderDecoderImpl(), new FrameBodyDecoderImpl());
        FrameReqWrapper wrapper = decoder.decodeFrame(buffer);
        System.out.println(
                wrapper.getReqDto().getBody().getElementMap().get(FrameConstant.BODY_ELEMENT_REVIER).get(0).getVal());
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmm");
        System.out.println(sd.parse(wrapper.getReqDto().getBody().getObserveTime()));
        return;
    }
}
