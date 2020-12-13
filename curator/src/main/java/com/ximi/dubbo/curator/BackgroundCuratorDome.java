package com.ximi.dubbo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;

/**
 * Curator Background
 *
 * @author Ximi
 * @since 2020/12/10
 */
public class BackgroundCuratorDome {

    public static void main(String[] args) {
        //创建客户端
        CuratorFramework client = BaseOperationCuratorDome.createClient();
        addListener(client);
        createZNode(client);
        lookupZNode(client);
        updateZNode(client);

        try {
            Thread.sleep(5000);
        } catch (Exception ex) {

        }

        deleteZnode(client);

        client.close();

    }

    private static void addListener(CuratorFramework client) {
        client.getCuratorListenable().addListener(new MyCuratorListener());
    }

    /**
     * Curator 的监听器
     */
    public static class MyCuratorListener implements CuratorListener {

        @Override
        public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
            switch (event.getType()) {
                case CREATE:
                    System.out.println("CREATE:" + event.getPath());
                    break;
                case DELETE:
                    System.out.println("DELETE:" + event.getPath());
                    break;
                case EXISTS:
                    System.out.println("EXISTS:" + event.getPath());
                    break;
                case GET_DATA:
                    System.out.println("GET_DATA:" + new String(event.getData()));
                    break;
                case SET_DATA:
                    System.out.println("SET_DATA:" + new String(event.getData()));
                    break;
                case CHILDREN:
                    System.out.println("CHILDREN:" + event.getChildren().toString());
                    break;
                default:
            }
        }
    }


    /**
     * 创建节点
     *
     * @param client
     */
    public static void createZNode(CuratorFramework client) {

        try {
            if (client.checkExists().forPath("/user") == null) {
                client.create().withMode(CreateMode.PERSISTENT).
                        inBackground().
                        forPath("/user", "test".getBytes());
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
            client.checkExists().inBackground().forPath("/user");
            //获取节点的数据
            client.getData().inBackground().forPath("/user");
            //查看子节点
            client.getChildren().inBackground().forPath("/user");
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
            client.setData().inBackground().forPath("/user", "data".getBytes());
            //创建子节点
            for (int index = 0; index < 3; index++) {
                String nodeChild = "/user/child-" + index;
                String nodeData = "child-" + index;
                if (client.checkExists().forPath(nodeChild) == null) {
                    client.create().withMode(CreateMode.EPHEMERAL).inBackground().forPath(nodeChild, nodeData.getBytes());
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
            client.delete().deletingChildrenIfNeeded().inBackground().forPath("/user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
