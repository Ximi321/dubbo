package com.ximi.dubbo.rpc.compressor;

/**
 * 压缩工具工厂
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class CompressorFactory {

    private static Compressor compressor = new SnappyCompressor();

    public static Compressor getCompressor() {
        return compressor;
    }

}
