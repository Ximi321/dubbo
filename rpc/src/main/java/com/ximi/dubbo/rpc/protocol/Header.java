package com.ximi.dubbo.rpc.protocol;

import com.ximi.dubbo.rpc.Constants;

/**
 * 协议的请求头
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class Header {

    private short magic = Constants.magic; //魔数
    private byte version = Constants.version; //版本
    private byte isHeart = 0; //是否是心跳请求
    private byte isRequest = 0;  //是否是响应
    private byte isCompressor = 0;  //是否压缩
    private long massageId;   //消息id
    private int contextSize;  //内容的长度

    public Header() {
    }

    public Header(short magic, byte version) {
        this.magic = magic;
        this.version = version;
    }

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getIsCompressor() {
        return isCompressor;
    }

    public void setIsCompressor(byte isCompressor) {
        this.isCompressor = isCompressor;
    }

    public byte getIsHeart() {
        return isHeart;
    }

    public void setIsHeart(byte isHeart) {
        this.isHeart = isHeart;
    }

    public byte getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(byte isRequest) {
        this.isRequest = isRequest;
    }

    public long getMassageId() {
        return massageId;
    }

    public void setMassageId(long massageId) {
        this.massageId = massageId;
    }

    public int getContextSize() {
        return contextSize;
    }

    public void setContextSize(int contextSize) {
        this.contextSize = contextSize;
    }
}
