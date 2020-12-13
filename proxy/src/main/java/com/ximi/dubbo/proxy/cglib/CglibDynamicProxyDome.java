package com.ximi.dubbo.proxy.cglib;

import com.ximi.dubbo.proxy.subject.ISubject;
import com.ximi.dubbo.proxy.subject.RealSubject;

/**
 * cglib 动态代理
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class CglibDynamicProxyDome {

    public static void main(String[] args) {

        ISubject subject = (ISubject) new ExecutedTimeInterceptor().getProxy(RealSubject.class);
        subject.operation();

    }

}
