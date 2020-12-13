package com.ximi.dubbo.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于 Cglib MethodInterceptor 的拦截
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class ExecutedTimeInterceptor implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long statTime = before();
        Object result = methodProxy.invokeSuper(o, objects);
        long endTime = after();
        System.out.printf("%s方法,时间:%s", method.getName(), endTime - statTime);
        return result;
    }

    private Long before() {
        return System.currentTimeMillis();
    }

    private Long after() {
        return System.currentTimeMillis();
    }
}
