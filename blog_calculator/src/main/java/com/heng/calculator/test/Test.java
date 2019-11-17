package com.heng.calculator.test;


import com.alibaba.fastjson.JSONObject;
import com.heng.calculator.utils.ESClientPool;
import com.heng.common.bean.User;
import com.heng.common.util.PropertyUtil;
import com.heng.util.ESClient;
import com.heng.util.ESTransfortClient;
import org.elasticsearch.common.settings.Settings;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        PropertyUtil util = PropertyUtil.getPropertyUtil("config.properties");
        String hostname = util.getProperty("es.host");
        String port = util.getProperty("es.port");
        Map<String,Object> param = new HashMap<>();
        param.put("cluster.name", util.getProperty("cluster.name"));
        ESClientPool esClientPool = new ESClientPool();
        esClientPool.setSettings(param);
        esClientPool.setHostname(hostname);
        esClientPool.setPort(Integer.valueOf(port));

        Map<String,Object> search = new HashMap<>();
        search.put("blogTilte", "博客");
        for (int i = 0;i < 6;i++){
            new Thread(new MyThread(esClientPool),"Thread-" + i).start();
        }

        synchronized (Test.class){
            Test.class.wait();
        }

    }
}
