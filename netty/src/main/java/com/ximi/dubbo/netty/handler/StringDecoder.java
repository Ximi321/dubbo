package com.ximi.dubbo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class StringDecoder extends ByteToMessageDecoder {

    public Integer messageLength = -1;
    public String message;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (messageLength == -1 && in.readableBytes() >= 4) {
            messageLength = in.readInt();
        }

        if (messageLength != -1 && in.readableBytes() >= messageLength) {
            byte[] bytes = new byte[messageLength];
            in.readBytes(bytes);
            message = new String(bytes);
            out.add(message);
            messageLength = -1;
        }
    }
}
