package com.ximi.dubbo.rpa.registry;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.List;

/**
 * 基于zookeeper 的服务发现和注册
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class ZookeeperRegistry implements Registry<ServiceInfo> {

    private ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private String root = "xrpa";
    private CuratorFramework client;
    private InstanceSerializer serializer = new JsonInstanceSerializer(ServiceInfo.class);
    private String address = "127.0.0.1:2181";

    public void start() throws Exception {

        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(address, policy);
        client.start();

        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .basePath(root)
                .client(client)
                .serializer(serializer)
                .watchInstances(true)
                .build();

        serviceDiscovery.start();

    }


    @Override
    public void registerService(ServiceInstance<ServiceInfo> serviceInstance) throws Exception {
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public void unRegisterService(ServiceInstance<ServiceInfo> serviceInstance) throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);
    }

    @Override
    public List<ServiceInstance<ServiceInfo>> getServiceInstance(String serviceName) throws Exception {
        return (List<ServiceInstance<ServiceInfo>>) serviceDiscovery.queryForInstances(serviceName);
    }
}
