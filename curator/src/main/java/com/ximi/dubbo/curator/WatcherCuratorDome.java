package com.ximi.dubbo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import java.io.IOException;

/**
 * Watch
 *
 * @author Ximi
 * @since 2020/12/11
 */
public class WatcherCuratorDome {

    public static void main(String[] args) throws IOException {

        CuratorFramework client = BaseOperationCuratorDome.createClient();
        BaseOperationCuratorDome.createZNode(client);
        addListener(client);

        System.in.read();

        client.close();

    }


    public static void addListener(CuratorFramework client) {
        try {
            client.checkExists().usingWatcher(new CuratorWatcher() {
                @Override
                public void process(WatchedEvent event) throws Exception {
                    System.out.println("checkExists: " + event.getPath());
                }
            }).forPath("/user");

            client.getData().usingWatcher(new CuratorWatcher() {
                @Override
                public void process(WatchedEvent event) throws Exception {
                    System.out.println("getData: " + event.getPath());
                }
            }).forPath("/user");

            client.getChildren().usingWatcher(new CuratorWatcher() {
                @Override
                public void process(WatchedEvent event) throws Exception {
                    System.out.println("getChildren: " + event.getPath());
                }
            }).forPath("/user");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
