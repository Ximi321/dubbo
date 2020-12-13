package com.ximi.dubbo.proxy.jdk;

import com.ximi.dubbo.proxy.subject.ISubject;
import com.ximi.dubbo.proxy.subject.RealSubject;

/**
 * jdk 动态代理的dome
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class JdkDynamicProxyDome {

    public static void main(String[] args) {

        ISubject subject = new RealSubject();
        ExecutedTimeHandler handler = new ExecutedTimeHandler(subject);
        ISubject proxySubject = (ISubject) handler.getProxy();
        proxySubject.operation();

    }
}
