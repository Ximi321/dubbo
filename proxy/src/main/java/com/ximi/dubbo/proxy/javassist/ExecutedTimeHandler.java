package com.ximi.dubbo.proxy.javassist;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * 基于 javassist 的动态代理
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class ExecutedTimeHandler implements MethodHandler, MethodFilter {

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        long statTime = before();
        Object object = proceed.invoke(self, args);
        long endTime = after();
        System.out.printf("[%s],%s方法执行的时间:%s", this.getClass().getSimpleName(), proceed.getName(), endTime - statTime);
        return object;
    }

    @Override
    public boolean isHandled(Method m) {
        if (m.getName().equals("operation")) {
            return true;
        }
        return false;
    }

    public Object getObject(Class clazz) throws IllegalAccessException, InstantiationException {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        proxyFactory.setFilter(this);
        Class<?> c = proxyFactory.createClass();
        Proxy proxy = (Proxy) c.newInstance();
        proxy.setHandler(this);
        return proxy;
    }

    private Long before() {
        return System.currentTimeMillis();
    }

    private Long after() {
        return System.currentTimeMillis();
    }
}
