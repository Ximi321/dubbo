package com.ximi.dubbo.rpc.transport;

import com.ximi.dubbo.rpc.codec.RpaDecoder;
import com.ximi.dubbo.rpc.codec.RpaEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty 客户端
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class NettyClient {

    private Bootstrap bootstrap;
    private NioEventLoopGroup group;

    private String host;
    private Integer port;

    public NettyClient(String host, Integer port) {

        this.host = host;
        this.port = port;

        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup(1);
        bootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("encode", new RpaEncoder());
                        ch.pipeline().addLast("decode", new RpaDecoder());
                        ch.pipeline().addLast("client-handler", new RpcClientHandler());
                    }
                });

    }

    public ChannelFuture connect() {
        ChannelFuture future = bootstrap.connect(host, port);
        future.syncUninterruptibly();
        return future;
    }

    public void close() {
        group.shutdownGracefully();
    }

}
