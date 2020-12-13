package com.ximi.dubbo.netty.client;

public class NetttClientDome {

    public static void main(String[] args) {

        NettyClient nettyClient = new NettyClient("127.0.0.1", 8090);
        nettyClient.sendMessage("hello world");

    }

}
