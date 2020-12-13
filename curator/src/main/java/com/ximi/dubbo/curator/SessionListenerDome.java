package com.ximi.dubbo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 链接状态的监听
 *
 * @author Ximi
 * @since 2020/12/10
 */
public class SessionListenerDome {

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = createClient();
        client.getConnectionStateListenable().addListener(new MyConnectionStateListener());
        //启动客户端
        client.start();
        Thread.sleep(10000);
        client.close();
    }

    /**
     * 创建客户端
     *
     * @return
     */
    public static CuratorFramework createClient() {
        //zookeeper 的地址
        String zkAddress = "127.0.0.1:2181";
        //设置轮询的策略,轮询3次,每次间隔1000毫秒
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //创建客户端
        CuratorFramework curatorClient = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        return curatorClient;
    }

    private static class MyConnectionStateListener implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {

            switch (newState) {
                case CONNECTED:
                    System.out.println("CONNECTED:");
                    break;
                case SUSPENDED:
                    System.out.println("SUSPENDED:");
                    break;
                case RECONNECTED:
                    System.out.println("RECONNECTED:");
                    break;
                case LOST:
                    System.out.println("LOST:");
                    break;
                case READ_ONLY:
                    System.out.println("READ_ONLY:");
                    break;
                default:
            }
        }
    }

}
