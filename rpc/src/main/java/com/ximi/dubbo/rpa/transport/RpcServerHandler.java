package com.ximi.dubbo.rpa.transport;

import com.ximi.dubbo.rpa.protocol.Message;
import com.ximi.dubbo.rpa.protocol.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端业务handler
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<Message<Request>> {

    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Request> msg) throws Exception {

        //如果是心跳请求直接跳过
        if (msg.getHeader().getIsHeart() == 1) {
            ctx.writeAndFlush(msg);
            return;
        }

        executorService.execute(new InvokeRunnable(msg, ctx));
    }
}
