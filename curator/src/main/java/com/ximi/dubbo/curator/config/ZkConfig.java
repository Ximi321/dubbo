package com.ximi.dubbo.curator.config;

/**
 *
 */
public class ZkConfig {

    private String zkHost;

    private Integer zkpost;

    public ZkConfig(String zkHost, Integer zkpost) {
        this.zkHost = zkHost;
        this.zkpost = zkpost;
    }

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public Integer getZkpost() {
        return zkpost;
    }

    public void setZkpost(Integer zkpost) {
        this.zkpost = zkpost;
    }

    public String getIpAddress(){
        return zkHost + ":" + zkpost;
    }
}
