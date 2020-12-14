package com.ximi.dubbo.netty.service;

import java.io.IOException;

public class NettyServierDome {

    public static void main(String[] args) throws IOException {

        NettyServer nettyServer = new NettyServer();
        nettyServer.doOpen("127.0.0.1",8090);

        System.in.read();


        nettyServer.close();

    }

}
