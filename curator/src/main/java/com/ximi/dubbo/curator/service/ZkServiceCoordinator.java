package com.ximi.dubbo.curator.service;

import com.ximi.dubbo.curator.config.ZkConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author Ximi
 * @since 2020/12/11
 */
public class ZkServiceCoordinator {

    private ServiceDiscovery<ServiceInfo> serviceDiscovery;

    private String root = "ximi";
    private CuratorFramework client;
    private InstanceSerializer serializer = new JsonInstanceSerializer(ServiceInfo.class);
    private ZkConfig config;
    private volatile Boolean initialize = false;

    public ZkServiceCoordinator(ZkConfig config) {
        this.config = config;
    }

    private synchronized void init() {
        if (initialize) return;
        client = createClient(config);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
                .basePath(root)
                .client(client)
                .serializer(serializer)
                .watchInstances(true)
                .build();

        try {
            serviceDiscovery.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize = true;
    }

    /**
     * @param config
     * @return
     */
    private CuratorFramework createClient(ZkConfig config) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(config.getIpAddress(), retryPolicy);
        client.start();
        return client;
    }

    /**
     * 注册服务
     *
     * @param serviceInfo
     */
    public void registerService(ServiceInfo serviceInfo) {
        init();
        try {
            ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
                    .name(serviceInfo.name)
                    .id(UUID.randomUUID().toString())
                    .address(serviceInfo.host)
                    .port(serviceInfo.port)
                    .payload(serviceInfo)
                    .build();
            serviceDiscovery.registerService(serviceInstance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<ServiceInfo> getRemoteService(String serviceName) {
        init();
        try {
            List<ServiceInfo> list = new ArrayList<>();
            Collection<ServiceInstance<ServiceInfo>> instances = serviceDiscovery.queryForInstances(serviceName);
            instances.forEach(infoServiceInstance -> {
                list.add(infoServiceInstance.getPayload());
            });
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        if(client != null){
            client.close();
        }
    }
}

