package com.ximi.dubbo.rpc.protocol;

/**
 * 消息
 *
 * @param <T>
 */
public class Message<T> {

    //消息头
    private Header header;
    //消息内容
    private T context;

    public Message(Header header, T context) {
        this.header = header;
        this.context = context;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getContext() {
        return context;
    }

    public void setContext(T context) {
        this.context = context;
    }
}
