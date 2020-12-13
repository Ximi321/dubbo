package com.ximi.dubbo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 执行时间的 InvocationHandler
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class ExecutedTimeHandler implements InvocationHandler {

    private Object target;

    public ExecutedTimeHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long statTime = before();
        Object object = method.invoke(target, args);
        long endTime = after();
        System.out.printf("%s方法执行的时间:%s", method.getName(), endTime - statTime);
        return object;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    private Long before() {
        return System.currentTimeMillis();
    }

    private Long after() {
        return System.currentTimeMillis();
    }
}
