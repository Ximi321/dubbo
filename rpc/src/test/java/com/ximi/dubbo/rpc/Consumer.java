package com.ximi.dubbo.rpc;

import com.ximi.dubbo.rpc.proxy.RpcProxy;
import com.ximi.dubbo.rpc.registry.ZookeeperRegistry;

/**
 * 消费者
 *
 * @author Ximi
 * @since 2020/12/16
 */
public class Consumer {

    public static void main(String[] args) {

        try {
            ZookeeperRegistry registry = new ZookeeperRegistry();
            registry.start();

            DomeService domeService = RpcProxy.newProxy(DomeService.class, registry);
            String message = domeService.test("hello world");
            System.out.println("message:" + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
