package com.ximi.dubbo.proxy.subject;

/**
 * 正事的操作
 *
 * @author Ximi
 * @since 2020/12/13
 */
public class RealSubject implements ISubject {

    @Override
    public void operation() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
