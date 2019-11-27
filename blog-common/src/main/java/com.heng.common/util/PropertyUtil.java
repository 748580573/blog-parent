package com.heng.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertyUtil {

    private Properties properties;

    private PropertyUtil(){}

    /**
     *
     * @param fileClasspath
     */
    private PropertyUtil(String fileClasspath){
        try {
            properties = new Properties();
            InputStream in = PropertyUtil.class.getClassLoader().getResourceAsStream(fileClasspath);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertyUtil getPropertyUtil(String fileClasspath){
        return new PropertyUtil(fileClasspath);
    }

    /**
     * 默认情况下读取类根路径下conf.properties文件
     * @return
     */
    public static PropertyUtil getPropertyUtil(){
        return getPropertyUtil("conf.properties");
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public Set<Map.Entry<Object, Object>> getEntry(){
        return properties.entrySet();
    }
}
