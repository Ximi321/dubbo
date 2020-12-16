package com.ximi.dubbo.rpc;

import com.ximi.dubbo.rpc.bean.SingletonBeanFactory;
import com.ximi.dubbo.rpc.registry.ServiceInfo;
import com.ximi.dubbo.rpc.registry.ZookeeperRegistry;
import com.ximi.dubbo.rpc.transport.NettyServer;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.UUID;

/**
 * 提供者
 *
 * @author Ximi
 * @since 2020/12/16
 */
public class Provider {

    public static void main(String[] args) {

        try {
            ZookeeperRegistry registry = new ZookeeperRegistry();
            registry.start();

            ServiceInfo serviceInfo = new ServiceInfo("127.0.0.1", 8090);
            ServiceInstance<ServiceInfo> serviceInstance = getServiceInstance(serviceInfo);
            registry.registerService(serviceInstance);

            SingletonBeanFactory.getBeanFactory().registerBean(DomeService.class.getName(), new DomeServiceImpl());

            NettyServer nettyServer = new NettyServer(8090);
            nettyServer.start();

            System.in.read();

            nettyServer.shotdown();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static ServiceInstance<ServiceInfo> getServiceInstance(ServiceInfo serviceInfo) throws Exception {
        ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
                .name("testRpc")
                .id(UUID.randomUUID().toString())
                .address(serviceInfo.host)
                .port(serviceInfo.port)
                .payload(serviceInfo)
                .build();
        return serviceInstance;
    }
}
