package com.example.fh.server.config;

import javax.annotation.Resource;

import com.example.fh.biz.service.BizService;
import com.example.fh.server.codec.FrameBodyDecoder;
import com.example.fh.server.codec.FrameEncoder;
import com.example.fh.server.codec.FrameHeaderDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



import lombok.Data;

/**
 * @author weijiayu
 * @date 2022/7/19 15:45
 */
@Configuration
@Data
public class NettyServerConfig {

    @Value("${server_socket_ip:127.0.0.1}")
    private String ipAddr;

    @Value("${server_socket_port:11111}")
    private int port;

    @Value("${crc_verify_flag:false}")
    private boolean crcVerifyFlag;

    @Value("${header_delimiter_flag:false}")
    private boolean headerDelimiterFlag;

    @Value("${netty_so_backlog:50}")
    private int nettySoBackLog;

    @Resource
    private FrameHeaderDecoder headerDecoder;
    @Resource
    private FrameBodyDecoder bodyDecoder;
    @Resource
    private BizService bizService;
    @Resource
    private FrameEncoder frameEncoder;
}
