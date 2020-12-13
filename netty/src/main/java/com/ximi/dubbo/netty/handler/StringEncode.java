package com.ximi.dubbo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class StringEncode extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.getBytes();
        if (bytes.length > 0) {
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
