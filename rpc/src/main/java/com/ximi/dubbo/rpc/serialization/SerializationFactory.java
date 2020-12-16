package com.ximi.dubbo.rpc.serialization;

public class SerializationFactory {

    private static Serialization serialization = new HessianSerialization();

    public static Serialization getSerialization() {
        return serialization;
    }
}
