package com.ximi.dubbo.netty.service;

public class NettyServierDome {

    public static void main(String[] args) {

        NettyServer nettyServer = new NettyServer();
        nettyServer.doOpen("127.0.0.1",8090);

    }

}
