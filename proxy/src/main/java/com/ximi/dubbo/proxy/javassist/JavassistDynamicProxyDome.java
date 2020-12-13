package com.ximi.dubbo.proxy.javassist;

import com.ximi.dubbo.proxy.subject.RealSubject;

/**
 * javassist 动态代码dome
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class JavassistDynamicProxyDome {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ExecutedTimeHandler handler = new ExecutedTimeHandler();
        RealSubject realSubject = (RealSubject) handler.getObject(RealSubject.class);
        realSubject.operation();
    }

}
