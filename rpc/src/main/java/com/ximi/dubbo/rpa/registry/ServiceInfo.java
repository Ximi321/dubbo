package com.ximi.dubbo.rpa.registry;


import java.io.Serializable;

/**
 * 服务实例
 *
 * @author Ximi
 * @since 2020/12/15
 */
public class ServiceInfo implements Serializable {

    public String host;

    public String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
