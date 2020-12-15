package com.ximi.dubbo.rpa.transport;

import com.ximi.dubbo.rpa.Constants;
import com.ximi.dubbo.rpa.protocol.Message;
import com.ximi.dubbo.rpa.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Rpa 客户端业务 handler
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class RpaClientHandler extends SimpleChannelInboundHandler<Message<Response>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message<Response> msg) throws Exception {

        NettyResponseFuture future = Connection.request_future_map.get(msg.getHeader().getMassageId());
        Response response = msg.getContext();
        //心跳请求
        if (msg.getHeader().getIsHeart() == 1) {
            response = new Response();
            response.setCode(Constants.heartCode);
        }
        future.getPromise().setSuccess(response);
    }
}
