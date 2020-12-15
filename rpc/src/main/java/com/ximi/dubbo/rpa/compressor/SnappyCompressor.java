package com.ximi.dubbo.rpa.compressor;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Snappy 压缩
 *
 * @author Ximi
 * @since
 */
public class SnappyCompressor implements Compressor {

    @Override
    public byte[] compress(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        return Snappy.compress(bytes);
    }

    @Override
    public byte[] uncompress(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        return Snappy.uncompress(bytes);
    }
}
