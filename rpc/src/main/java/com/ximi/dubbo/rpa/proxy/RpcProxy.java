package com.ximi.dubbo.rpa.proxy;

import com.ximi.dubbo.rpa.registry.ServiceInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 客户端RPA 代理
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class RpcProxy implements InvocationHandler {

    private String serviceName;
    private List<ServiceInfo> serviceInfos;

    public RpcProxy(String serviceName, List<ServiceInfo> serviceInfos) {
        this.serviceName = serviceName;
        this.serviceInfos = serviceInfos;
    }

    public static <T> T newProxy(Class<T> clazz, List<ServiceInfo> serviceInfos) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz}, new RpcProxy(clazz.getName(), serviceInfos));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
