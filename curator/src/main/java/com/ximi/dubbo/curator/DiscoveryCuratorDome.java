package com.ximi.dubbo.curator;

import com.ximi.dubbo.curator.config.ZkConfig;
import com.ximi.dubbo.curator.service.ServiceInfo;
import com.ximi.dubbo.curator.service.ZkServiceCoordinator;

import java.util.List;

public class DiscoveryCuratorDome {

    public static void main(String[] args) {

        ZkConfig zkConfig = new ZkConfig("127.0.0.1", 2181);
        ZkServiceCoordinator coordinator = new ZkServiceCoordinator(zkConfig);
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setName("service1");
        serviceInfo.setHost("127.0.0.1");
        serviceInfo.setPort(8080);
        coordinator.registerService(serviceInfo);

        List<ServiceInfo> serviceInfos = coordinator.getRemoteService(serviceInfo.getName());
        serviceInfos.forEach(System.out::println);

        coordinator.close();
    }

}
