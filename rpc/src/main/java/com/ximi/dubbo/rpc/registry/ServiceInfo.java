package com.ximi.dubbo.rpc.registry;


import java.io.Serializable;

/**
 * 服务实例
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class ServiceInfo implements Serializable {

    public String host;

    public Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ServiceInfo() {
    }

    public ServiceInfo(String host, Integer port) {
        this.host = host;
        this.port = port;
    }
}
