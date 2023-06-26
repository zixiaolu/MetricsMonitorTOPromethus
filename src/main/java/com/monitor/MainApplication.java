package com.monitor;

import com.monitor.tool.constructfile.FileSdManager;
import com.monitor.tool.zookeeper.IWatcher.Default;
import com.monitor.tool.zookeeper.ZookeeperClient;

import java.util.List;

public class MainApplication {

    public static void main(String[] args) {
        FileSdManager.init();

    }
}
