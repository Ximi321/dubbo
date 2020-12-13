package com.ximi.dubbo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;

import java.io.IOException;


/**
 * @author Ximi
 * @see NodeCache
 * @see TreeCache
 * @see PathChildrenCache
 * @since 2020/12/11
 */
public class CacheCuratorDome {

    public static void main(String[] args) throws IOException {
        CuratorFramework client = BaseOperationCuratorDome.createClient();
        addNodeCache(client);
        addPathChildrenCache(client);
        addTreeCache(client);

        System.in.read();
        client.close();

    }

    public static void addNodeCache(CuratorFramework client) {

        try {
            NodeCache nodeCache = new NodeCache(client, "/user");
            nodeCache.start(true);
            nodeCache.getListenable().addListener(() -> {
                print("nodeCache", nodeCache.getPath(), nodeCache.getCurrentData().getData());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPathChildrenCache(CuratorFramework client) {
        try {
            PathChildrenCache childrenCache = new PathChildrenCache(client, "/user", true);
            childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            childrenCache.getListenable().addListener((curator, event) -> {

                switch (event.getType()) {
                    case CHILD_ADDED:
                        print("CHILD_ADDED", event.getData().getPath(), event.getData().getData());
                        break;
                    case CHILD_UPDATED:
                        print("CHILD_UPDATED", event.getData().getPath(), event.getData().getData());
                        break;
                    case CHILD_REMOVED:
                        print("CHILD_UPDATED", event.getData().getPath(), event.getData().getData());
                        break;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addTreeCache(CuratorFramework client) {
        try {
            TreeCache treeCache = new TreeCache(client, "/user");
            treeCache.start();
            treeCache.getListenable().addListener((curator, event) ->{
                switch (event.getType()){
                    case NODE_ADDED:
                        print("NODE_ADDED", event.getData().getPath(), event.getData().getData());
                        break;
                    case NODE_UPDATED:
                        print("NODE_UPDATED", event.getData().getPath(), event.getData().getData());
                        break;
                    case NODE_REMOVED:
                        print("NODE_REMOVED", event.getData().getPath(), event.getData().getData());
                        break;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(String tag, String path, byte[] data) {
        System.out.println(String.format("Tag:%s#节点:%s#数据:%s", tag, path, new String(data)));
    }
}
