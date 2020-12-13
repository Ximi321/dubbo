package com.ximi.dubbo.netty.service;

import com.ximi.dubbo.netty.handler.ServerHandler;
import com.ximi.dubbo.netty.handler.StringDecoder;
import com.ximi.dubbo.netty.handler.StringEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * Netty Service
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class NettyServer {

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;

    private Channel channel;

    private ServerBootstrap bootstrap;

    public void doOpen(String host, Integer port) {
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("bossGroup", false));
        workGroup = new NioEventLoopGroup(8, new DefaultThreadFactory("workGroup", false));

        bootstrap.group(bossGroup, workGroup);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        ch.pipeline().addLast("encode", new StringEncode());
                        ch.pipeline().addLast("server-handler", new ServerHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.bind(host, port);
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();
    }

}
