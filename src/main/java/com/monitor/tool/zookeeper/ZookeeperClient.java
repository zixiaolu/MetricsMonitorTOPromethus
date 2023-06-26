package com.monitor.tool.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZookeeperClient {

    private static int TIMEOUT_MILLIS = 5000;

    private static String ROOT = "/DubboMetricsMonitor";

    public static ZooKeeper getClient(String Url,Watcher method)
    {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(Url,TIMEOUT_MILLIS,method);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }

}
