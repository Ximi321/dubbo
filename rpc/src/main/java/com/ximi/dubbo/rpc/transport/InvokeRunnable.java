package com.ximi.dubbo.rpc.transport;

import com.ximi.dubbo.rpc.bean.SingletonBeanFactory;
import com.ximi.dubbo.rpc.protocol.Header;
import com.ximi.dubbo.rpc.protocol.Message;
import com.ximi.dubbo.rpc.protocol.Request;
import com.ximi.dubbo.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

/**
 * server 调用
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class InvokeRunnable implements Runnable {

    private Message<Request> message;
    private ChannelHandlerContext ctx;

    public InvokeRunnable(Message<Request> message, ChannelHandlerContext ctx) {
        this.message = message;
        this.ctx = ctx;
    }

    @Override
    public void run() {

        Response response = new Response();

        try {
            Request request = message.getContext();
            Object server = SingletonBeanFactory.getBeanFactory().getBean(request.getServiceName());
            Method method = server.getClass().getDeclaredMethod(request.getMethodName(), request.getParameterType());
            Object result = method.invoke(server, request.getParameterValue());
            response.setCode(200);
            response.setResult(result);

        } catch (Exception e) {
            response.setCode(500);
        }

        Header header = message.getHeader();
        header.setIsRequest((byte) 0);
        Message message = new Message(header, response);
        ctx.writeAndFlush(message);
    }
}
