package com.monitor;

import com.monitor.tool.constructfile.FileSdManager;
import com.monitor.tool.zookeeper.IWatcher.Default;
import com.monitor.tool.zookeeper.ZookeeperClient;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class MainApplication {

    public static Stat stat = new Stat();

    public static void main(String[] args) {
        FileSdManager.init();
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = ZookeeperClient.getClient("10.2.131.75:2181",new Default());
            while(true)
            {
                List<String> dataList = zooKeeper.getChildren("/DubboMetricsMonitor",false);
                for(String childUrl : dataList)
                {
                    byte[] data = zooKeeper.getData("/DubboMetricsMonitor"+"/"+childUrl,false,stat);
                    String dataInfo = new String(data);
                    FileSdManager.refreshCheckByTargetInMetrics(dataInfo);
                }
                FileSdManager.refresh();
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
