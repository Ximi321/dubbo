package com.ximi.dubbo.rpa.transport;

import com.ximi.dubbo.rpa.protocol.Message;
import com.ximi.dubbo.rpa.protocol.Request;
import com.ximi.dubbo.rpa.protocol.Response;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端连接的操作
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class Connection {

    private static AtomicInteger autoIncreaseId = new AtomicInteger(0);

    public static Map<Long, NettyResponseFuture<Response>> request_future_map = new ConcurrentHashMap<>();

    private ChannelFuture future;

    public Connection(ChannelFuture future) {
        this.future = future;
    }

    public NettyResponseFuture request(Message<Request> message, long timeOut) {

        long messageId = autoIncreaseId.incrementAndGet();
        message.getHeader().setMassageId(messageId);

        NettyResponseFuture<Response> responseFuture = new NettyResponseFuture<Response>(System.currentTimeMillis(),
                timeOut, future.channel(), message, new DefaultPromise(new DefaultEventLoop()));

        request_future_map.put(messageId, responseFuture);

        try {
            future.channel().writeAndFlush(message);
        } catch (Exception ex) {
            request_future_map.remove(messageId);
            throw ex;
        }

        return responseFuture;
    }

}
