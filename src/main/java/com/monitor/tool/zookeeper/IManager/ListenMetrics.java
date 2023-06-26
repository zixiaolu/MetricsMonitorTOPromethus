package com.monitor.tool.zookeeper.IManager;

import com.monitor.beans.PrometheusFileSdData;
import com.monitor.config.InitConfig;
import com.monitor.config.beans.MonitorConfig;
import com.monitor.tool.constructfile.FileSdManager;
import com.monitor.tool.zookeeper.ZookeeperClient;
import com.monitor.utils.IPAddressValidator;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

public class ListenMetrics {
    public void monitor() throws InterruptedException {
        MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
        CuratorFramework client = ZookeeperClient.getClient();
        CuratorCache curatorCache = CuratorCache.builder(client,monitorConfig.getMonitorPath()).build();
        Listenable<CuratorCacheListener> listenable = curatorCache.listenable();
        listenable.addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData oldData, ChildData newData) {
                PrometheusFileSdData prometheusFileSdData = FileSdManager.getMetricsSd();
                String oldIp = "";
                String newIp = "";
                if(oldData!=null)
                {
                    oldIp = new String(oldData.getData());
                }
                if(newData!=null)
                {
                    newIp = new String(newData.getData());
                }
                switch (type)
                {
                    case NODE_CHANGED:
                        if(IPAddressValidator.isValidIPPort(oldIp)&&
                           IPAddressValidator.isValidIPPort(newIp))
                        {
                            prometheusFileSdData.getTargets().remove(oldIp);
                            if(!prometheusFileSdData.getTargets().contains(newIp))
                            {
                                prometheusFileSdData.getTargets().add(newIp);
                                FileSdManager.needRefresh();
                            }
                            System.out.println("node change-->Path:"+ oldData.getPath());
                        }
                        break;
                    case NODE_DELETED:
                        if(IPAddressValidator.isValidIPPort(oldIp))
                        {
                            prometheusFileSdData.getTargets().remove(oldIp);
                            FileSdManager.needRefresh();
                            System.out.println("node delete-->Path:"+ oldData.getPath());
                        }
                        break;
                    case NODE_CREATED:
                        if(IPAddressValidator.isValidIPPort(newIp))
                        {
                            if(!prometheusFileSdData.getTargets().contains(newIp))
                            {
                                prometheusFileSdData.getTargets().add(newIp);
                                FileSdManager.needRefresh();
                            }
                            System.out.println("node create-->Path:"+ newData.getPath());
                        }
                        break;
                }
            }
        });
        try {
            curatorCache.start();
            Thread.currentThread().join();
        }finally {
            curatorCache.close();
            client.close();
        }
    }
}
