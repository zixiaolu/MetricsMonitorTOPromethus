package com.monitor.config.beans;

public class MonitorConfig {
    String zookeeperIp;
    String monitorPath;
    int timeOut;
    long refreshTime;
    String fileName;
    String env;
    String job;

    public String getZookeeperIp() {
        return zookeeperIp;
    }

    public void setZookeeperIp(String zookeeperIp) {
        this.zookeeperIp = zookeeperIp;
    }

    public String getMonitorPath() {
        return monitorPath;
    }

    public void setMonitorPath(String monitorPath) {
        this.monitorPath = monitorPath;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
