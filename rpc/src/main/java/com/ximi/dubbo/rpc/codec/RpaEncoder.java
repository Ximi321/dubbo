package com.ximi.dubbo.rpc.codec;

import com.ximi.dubbo.rpc.Constants;
import com.ximi.dubbo.rpc.compressor.CompressorFactory;
import com.ximi.dubbo.rpc.protocol.Header;
import com.ximi.dubbo.rpc.protocol.Message;
import com.ximi.dubbo.rpc.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class RpaEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getIsHeart());
        out.writeByte(header.getIsRequest());
        out.writeByte(header.getIsCompressor());
        out.writeLong(header.getMassageId());

        if (header.getMagic() != Constants.magic) {
            throw new IllegalStateException("header magic error");
        }

        if (header.getVersion() != Constants.version) {
            throw new IllegalStateException("header version error");
        }

        //是心跳请求
        if (header.getIsHeart() == 1) {
            out.writeInt(0);
            return;
        }

        byte[] payload = SerializationFactory.getSerialization().serialize(msg.getContext());

        //判断是否要压缩
        if (header.getIsCompressor() == 1) {
            payload = CompressorFactory.getCompressor().compress(payload);
        }

        out.writeInt(payload.length);
        out.writeBytes(payload);
    }
}
