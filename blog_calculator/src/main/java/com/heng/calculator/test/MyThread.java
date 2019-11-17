package com.heng.calculator.test;

import com.heng.calculator.utils.ESClientPool;
import com.heng.util.ESClient;

import java.util.HashMap;
import java.util.Map;

public class MyThread implements Runnable {

    ESClientPool pool;

    public MyThread(ESClientPool pool){
        this.pool = pool;
    }
    @Override
    public void run() {
        Map<String,Object> search = new HashMap<>();
        search.put("blogTilte", "博客");
        ESClient client = pool.getClient();
        client.getDataByShouldQuery("blog", "Blog", search);
    }
}
