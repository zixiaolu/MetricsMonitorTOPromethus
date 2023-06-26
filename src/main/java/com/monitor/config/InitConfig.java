package com.monitor.config;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import com.monitor.config.beans.MonitorConfig;

import java.io.IOException;
import java.io.InputStream;

public class InitConfig {

    private static MonitorConfig monitorConfig;

    public static MonitorConfig getMonitorConfig(){

        if(monitorConfig==null)
        {
            try {
                InputStream content = InitConfig.class.getClassLoader().getResourceAsStream("monitorConfig.toml");
                TomlMapper tomlMapper = new TomlMapper();
                monitorConfig = tomlMapper.readValue(content, MonitorConfig.class);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return monitorConfig;
    }
}
