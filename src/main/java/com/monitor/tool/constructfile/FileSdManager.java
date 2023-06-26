package com.monitor.tool.constructfile;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.monitor.Constant;
import com.monitor.beans.PrometheusFileSdData;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSdManager {

    public static List<PrometheusFileSdData> dataList = new ArrayList<PrometheusFileSdData>();

    public static boolean isRefresh = false;

    public static String fileName = "sd.json";

    public static void init()
    {
        File file = new File(fileName);
        try {
            String data = FileUtils.readFileToString(file,"UTF-8");
            JSONArray json = JSONArray.parseArray(data);
            PrometheusFileSdData sdData = null;
            if(json==null||json.size()<=0)
            {
                sdData = new PrometheusFileSdData();
                sdData.getLabels().put(Constant.ENV,"dev");
                sdData.getLabels().put(Constant.JOB,"dubbo-monitor");
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

    public static void refreshCheckByTargetInMetrics(String addIp)
    {
        if(addIp.isEmpty()) return ;
        if(dataList.isEmpty())
        {
            PrometheusFileSdData prometheusFileSdData = new PrometheusFileSdData();
            dataList.add(prometheusFileSdData);
        }
        PrometheusFileSdData data = dataList.get(0);
        if(!data.getTargets().contains(addIp))
        {
            isRefresh = true;
            data.getTargets().add(addIp);
        }
    }

    public static void refresh()
    {
        if(isRefresh)
        {
            isRefresh = false;
            JSONArray jsonArray = JSONArray.from(dataList);
            String dataInfo = jsonArray.toJSONString();
            File file = new File(fileName);
            try {
                FileUtils.writeByteArrayToFile(file,dataInfo.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
