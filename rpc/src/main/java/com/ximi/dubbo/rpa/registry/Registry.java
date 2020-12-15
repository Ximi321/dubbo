package com.ximi.dubbo.rpa.registry;

import org.apache.curator.x.discovery.ServiceInstance;

import java.io.IOException;
import java.util.List;

public interface Registry<T> {

    /**
     * 注册一个服务实例
     *
     * @param serviceInstance
     */
    public void registerService(ServiceInstance<T> serviceInstance) throws Exception;

    /**
     * 取消注册一个服务
     *
     * @param serviceInstance
     */
    public void unRegisterService(ServiceInstance<T> serviceInstance) throws Exception;

    /**
     * 根据服务名称获取服务实例
     *
     * @param serviceName
     * @return
     */
    public List<ServiceInstance<T>> getServiceInstance(String serviceName) throws Exception;

}
