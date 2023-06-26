package com.monitor.tool.zookeeper;


import com.monitor.config.InitConfig;
import com.monitor.config.beans.MonitorConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class ZookeeperClient {

    private static int MAX_RETRIES = 3;

    private static int SLEEP_TIME = 1000;

    private static CuratorFramework CLIENT;

    public static CuratorFramework getClient() {
        if(CLIENT == null)
        {
            MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
            ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(SLEEP_TIME, MAX_RETRIES);
            CLIENT = CuratorFrameworkFactory
                    .builder()
                    .connectString(monitorConfig.getZookeeperIp())
                    .sessionTimeoutMs(monitorConfig.getTimeOut())
                    .retryPolicy(retryPolicy)
                    .build();
        }
        return CLIENT;
    }
}
