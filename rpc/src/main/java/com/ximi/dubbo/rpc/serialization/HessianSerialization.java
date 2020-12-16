package com.ximi.dubbo.rpc.serialization;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian 序列化
 *
 * @author Ximi
 * @since 2020/12/14
 */
public class HessianSerialization implements Serialization {

    @Override
    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HessianOutput output = new HessianOutput(stream);
        output.writeObject(object);
        return stream.toByteArray();
    }

    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> clazz) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        HessianInput hessianInput = new HessianInput(inputStream);
        return (T) hessianInput.readObject(clazz);
    }
}
