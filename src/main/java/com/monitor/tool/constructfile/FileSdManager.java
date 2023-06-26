package com.monitor.tool.constructfile;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.monitor.Constant;
import com.monitor.beans.PrometheusFileSdData;
import com.monitor.config.InitConfig;
import com.monitor.config.beans.MonitorConfig;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSdManager {

    public static List<PrometheusFileSdData> dataList = new ArrayList<PrometheusFileSdData>();

    private static volatile boolean isRefresh = false;

    public static void init() throws IOException {

        MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
        File file = new File(monitorConfig.getFileName());
        if(!file.exists())
        {
            file.createNewFile();
        }
        try {
            String data = FileUtils.readFileToString(file,"UTF-8");
            JSONArray json = JSONArray.parseArray(data);
            PrometheusFileSdData sdData = null;
            if(json==null||json.size()<=0)
            {
                sdData = new PrometheusFileSdData();
                sdData.getLabels().put(Constant.ENV,monitorConfig.getEnv());
                sdData.getLabels().put(Constant.JOB,monitorConfig.getJob());
                dataList.add(sdData);
            }
            else
            {
                for(int i = 0 ;i<json.size();i++)
                {
                    sdData = JSONObject.parseObject(json.getString(i),PrometheusFileSdData.class);
                    dataList.add(sdData);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void refresh()
    {
        if(isRefresh)
        {
            MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
            isRefresh = false;
            JSONArray jsonArray = JSONArray.from(dataList);
            String dataInfo = jsonArray.toJSONString();
            File file = new File(monitorConfig.getFileName());
            try {
                FileUtils.writeByteArrayToFile(file,dataInfo.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static PrometheusFileSdData getMetricsSd()
    {
        if(dataList.isEmpty())
        {
            MonitorConfig monitorConfig = InitConfig.getMonitorConfig();
            PrometheusFileSdData sdData = new PrometheusFileSdData();
            sdData.getLabels().put(Constant.ENV,monitorConfig.getEnv());
            sdData.getLabels().put(Constant.JOB,monitorConfig.getJob());
            dataList.add(sdData);
        }
        return dataList.get(0);
    }

    public static void needRefresh()
    {
        isRefresh = true;
    }
}
