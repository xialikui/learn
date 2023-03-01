package com.example.quartz.job.service.impl;


import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ExcessWebSocketClient {

    private static Logger logger = LoggerFactory.getLogger(ExcessWebSocketClient.class);

    private WebSocketClient wsc;

    public void connect() {
        try {
            //  URI uri = new URI("ws://222.188.116.18:9082/notice/websocket");
            URI uri = new URI("ws://127.0.0.1:8089/quartz/");
            wsc = new WebSocketClient(uri) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    logger.info("socket已连接，开始发送消息");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    logger.info("socket已关闭");
                }

                @Override
                public void onError(Exception ex) {
                    logger.info("连接失败！");
                }
            };
            logger.info("socket开始连接！");
            wsc.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        if (!wsc.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            wsc.reconnect();
        }
        if (wsc.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            wsc.send(message);
        }
    }
}
