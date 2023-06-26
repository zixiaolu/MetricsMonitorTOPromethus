package com.monitor;

import com.monitor.config.InitConfig;
import com.monitor.config.beans.MonitorConfig;
import com.monitor.tool.constructfile.FileSdManager;
import com.monitor.tool.zookeeper.IManager.ListenMetrics;

import java.io.IOException;


public class MainApplication {

    public static void main(String[] args) throws InterruptedException, IOException {
        MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
        FileSdManager.init();
        ListenMetrics listenMetrics = new ListenMetrics();
        //多线程集中刷新数据
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true)
                    {
                        Thread.sleep(monitorConfig.getRefreshTime());
                        FileSdManager.refresh();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        //客户端监听节点修改
        listenMetrics.monitor();
    }
}
