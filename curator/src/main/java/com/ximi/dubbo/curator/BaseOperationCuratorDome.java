package com.ximi.dubbo.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Curator 基本操作
 *
 * @author Ximi
 * @since 2020/12/09
 */
public class BaseOperationCuratorDome {

    public static void main(String[] args) {
        CuratorFramework cilent = createClient();
        createZNode(cilent);
        lookupZNode(cilent);
        updateZNode(cilent);
        deleteZnode(cilent);
        //关闭客户端
        cilent.close();
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
        //启动客户端
        curatorClient.start();
        return curatorClient;
    }

    /**
     * 创建节点
     *
     * @param client
     */
    public static void createZNode(CuratorFramework client) {

        try {
            if (client.checkExists().forPath("/user") == null) {
                String path = client.create().withMode(CreateMode.PERSISTENT).forPath("/user", "test".getBytes());
                System.out.println("path:" + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看节点
     *
     * @param client
     */
    public static void lookupZNode(CuratorFramework client) {
        try {
            //查看节点是否存在
            Stat stat = client.checkExists().forPath("/user");
            if (null == stat) {
                System.out.printf("节点不存在");
                return;
            }
            //获取节点的数据
            byte[] data = client.getData().forPath("/user");
            System.out.println("节点数据:" + new String(data));

            List<String> childZnodes = client.getChildren().forPath("/user");
            childZnodes.forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 更新节点
     *
     * @param client
     */
    public static void updateZNode(CuratorFramework client) {
        try {
            client.setData().forPath("/user", "data".getBytes());
            //创建子节点
            for (int index = 0; index < 3; index++) {
                String nodeChild = "/user/child-" + index;
                String nodeData = "child-" + index;
                if(client.checkExists().forPath(nodeChild) == null){
                    client.create().withMode(CreateMode.EPHEMERAL).forPath(nodeChild, nodeData.getBytes());
                }
            }
            lookupZNode(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     *
     * @param client
     */
    public static void deleteZnode(CuratorFramework client) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath("/user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

