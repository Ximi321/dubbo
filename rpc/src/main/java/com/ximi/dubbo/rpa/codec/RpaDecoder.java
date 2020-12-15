package com.ximi.dubbo.rpa.codec;

import com.ximi.dubbo.rpa.Constants;
import com.ximi.dubbo.rpa.compressor.CompressorFactory;
import com.ximi.dubbo.rpa.protocol.Header;
import com.ximi.dubbo.rpa.protocol.Message;
import com.ximi.dubbo.rpa.protocol.Request;
import com.ximi.dubbo.rpa.protocol.Response;
import com.ximi.dubbo.rpa.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * RAP 解码
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class RpaDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < Constants.headerLength) {
            return;
        }

        in.markReaderIndex();

        Header header = new Header();
        header.setMagic(in.readShort());
        header.setVersion(in.readByte());
        header.setIsHeart(in.readByte());
        header.setIsRequest(in.readByte());
        header.setIsCompressor(in.readByte());
        header.setMassageId(in.readLong());
        header.setContextSize(in.readInt());

        if (header.getMagic() != Constants.magic) {
            throw new IllegalStateException("header magic error");
        }

        if (header.getVersion() != Constants.version) {
            throw new IllegalStateException("header version error");
        }

        if(in.readableBytes() < header.getContextSize()){
            in.resetReaderIndex();
            return;
        }

        Object object = null;
        if(header.getIsHeart() != 1){

            byte[] payload = new byte[header.getContextSize()];
            in.readBytes(payload);

            if(header.getIsCompressor() == 1){
                payload = CompressorFactory.getCompressor().uncompress(payload);
            }

            if(header.getIsRequest() == 1){
                object = SerializationFactory.getSerialization().deSerialize(payload, Request.class);
            }else {
                object = SerializationFactory.getSerialization().deSerialize(payload, Response.class);
            }

        }

        Message message = new Message(header,object);
        out.add(message);
    }
}
