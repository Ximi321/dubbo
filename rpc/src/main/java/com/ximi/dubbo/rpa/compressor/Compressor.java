package com.ximi.dubbo.rpa.compressor;

import java.io.IOException;

/**
 * 压缩
 *
 * @author Ximi
 * @since 2020/12/14
 */
public interface Compressor {

    /**
     * 压缩
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public byte[] compress(byte[] bytes) throws IOException;

    /**
     * 解压缩
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public byte[] uncompress(byte[] bytes) throws IOException;

}
