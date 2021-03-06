package com.ximi.dubbo.rpc.transport;


import com.ximi.dubbo.rpc.codec.RpaDecoder;
import com.ximi.dubbo.rpc.codec.RpaEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty 服务端
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class NettyServer {

    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;

    private Channel channel;
    private Integer port;

    public NettyServer(Integer port) {
        this.port = port;

        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(8);
        workGroup = new NioEventLoopGroup(8);

        serverBootstrap.group(bossGroup, workGroup)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("encode", new RpaEncoder());
                        ch.pipeline().addLast("decode", new RpaDecoder());
                        ch.pipeline().addLast("server-handler", new RpcServerHandler());
                    }
                });

    }

    public ChannelFuture start() {
        ChannelFuture channelFuture = serverBootstrap.bind(port);
        channel = channelFuture.channel();
        channel.closeFuture();
        return channelFuture;
    }

    public void startAndAwait() {
        try {
            channel.closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shotdown() {
        try {
            channel.close().sync();
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workGroup != null) {
                workGroup.shutdownGracefully();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
