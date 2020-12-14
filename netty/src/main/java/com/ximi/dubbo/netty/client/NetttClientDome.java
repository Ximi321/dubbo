package com.ximi.dubbo.netty.client;

import java.io.IOException;

/**
 * netty 客户端dome
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class NetttClientDome {

    public static void main(String[] args) throws IOException {

        NettyClient nettyClient = new NettyClient("127.0.0.1", 8090);
        nettyClient.sendMessage("hello world");
        nettyClient.sendMessage("welcome to this world");

        System.in.read();

        nettyClient.close();
    }

}
