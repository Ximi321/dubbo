package com.ximi.dubbo.netty.client;

import com.ximi.dubbo.netty.ClientHandler;
import com.ximi.dubbo.netty.handler.StringDecoder;
import com.ximi.dubbo.netty.handler.StringEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private Bootstrap bootstrap;
    private String host;
    private Integer port;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
        init();
    }

    private void init() {

        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup(1));
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("encode", new StringEncode());
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast("client-handler", new ClientHandler());
                    }
                });

    }


    public void sendMessage(String message) {
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.syncUninterruptibly();
        channelFuture.channel().writeAndFlush(message);
    }

}
