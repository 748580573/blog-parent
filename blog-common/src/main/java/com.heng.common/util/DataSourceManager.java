package com.heng.common.util;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataSourceManager {

    private static HashMap<String,DataSource> dataSourceHashMap = new HashMap<String, DataSource>();

    public DataSourceManager(){
        super();
    }

    public static void put(String key,DataSource dataSource){
        dataSourceHashMap.put(key, dataSource);
    }

    public static DataSource get(String key){
        return dataSourceHashMap.get(key);
    }

    public static Set<Map.Entry<String, DataSource>> Entry(){
        return dataSourceHashMap.entrySet();
    }
}
