package com.monitor.tool.zookeeper.IManager;

import com.monitor.beans.PrometheusFileSdData;
import com.monitor.config.InitConfig;
import com.monitor.config.beans.MonitorConfig;
import com.monitor.tool.constructfile.FileSdManager;
import com.monitor.tool.zookeeper.ZookeeperClient;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class InitMetrics {
    public void init()
    {
        MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
        PrometheusFileSdData prometheusFileSdData = FileSdManager.getMetricsSd();
        CuratorFramework client = ZookeeperClient.getClient();
        client.start();
        try {
            List<String> childData = client.getChildren().forPath(monitorConfig.getMonitorPath());
            for(String s : childData)
            {
                String data = new String(client.getData().forPath(monitorConfig.getMonitorPath()+"/"+s));
                if(!prometheusFileSdData.getTargets().contains(data))
                {
                    prometheusFileSdData.getTargets().add(data);
                    FileSdManager.needRefresh();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
