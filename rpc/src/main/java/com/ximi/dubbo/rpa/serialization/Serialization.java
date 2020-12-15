package com.ximi.dubbo.rpa.serialization;

import java.io.IOException;

/**
 * 序列化
 *
 * @author Ximi
 * @since 2020/12/14
 */
public interface Serialization {

    /**
     * 序列化接口
     *
     * @param T
     * @return
     * @throws IOException
     */
    public byte[] serialize(Object object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T deSerialize(byte[] bytes, Class<T> clazz) throws IOException;

}
