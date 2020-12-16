package com.ximi.dubbo.rpc.transport;

import com.ximi.dubbo.rpc.protocol.Message;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;


/**
 * 客户端等待响应的future
 *
 * @param <T>
 * @author Ximi
 * @since 2020/12/15
 */
public class NettyResponseFuture<T> {

    private long createTime;
    private long timeOut;
    private Channel channel;
    private Message request;
    private Promise<T> promise;

    public NettyResponseFuture(long createTime, long timeOut, Channel channel, Message request, Promise<T> promise) {
        this.createTime = createTime;
        this.timeOut = timeOut;
        this.channel = channel;
        this.request = request;
        this.promise = promise;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Message getRequest() {
        return request;
    }

    public void setRequest(Message request) {
        this.request = request;
    }

    public Promise<T> getPromise() {
        return promise;
    }

    public void setPromise(Promise<T> promise) {
        this.promise = promise;
    }
}
