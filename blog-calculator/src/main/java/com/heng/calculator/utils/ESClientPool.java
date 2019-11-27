package com.heng.calculator.utils;

import com.heng.util.ESClient;
import com.heng.util.ESTransfortClient;
import org.elasticsearch.common.settings.Settings;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ESClientPool {

    private static ConnectThreadLocal<ESClient> threadLocal = new ConnectThreadLocal();

    private volatile LinkedBlockingDeque<ESClient> pool;                        //未使用的连接

    private volatile Queue<ESClient> usingPool;                   //正在被使用的连接

    private int minimun = 3;

    private int maximun = 6;

    private volatile int currentSize;

    private int initSize = 3;

    private int idleTime = 30;             //空闲时间，单位秒

    private Settings settings;              //es配置

    private String hostname;                //主机名

    private int port;                       //es端口

    public ESClientPool(int capacity){
        super();
        this.initSize = capacity;
        pool = new LinkedBlockingDeque<>(this.maximun);
        currentSize = pool.size();
        usingPool = new LinkedBlockingDeque<>();
        Thread recyclingThread = new Thread(new RecycliingThread(pool,usingPool));
        init();
        recyclingThread.start();
    }

    public ESClientPool(){
        this(3);
    }


    class ESClientProxy implements InvocationHandler{

        private ESClient client;

        public  ESClient getESClientProxy(ESClient client){
            this.client = client;
            return (ESClient) Proxy.newProxyInstance(client.getClass().getClassLoader(), client.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object object = method.invoke(client, args);
            if ("close".equals(method.getName())){
                if (usingPool.remove(client)){
                    pool.add(client);
                }
            }
            if ("getLastUsedTime".equalsIgnoreCase(method.getName())){
                return object;
            }
            this.client.setLastUsedTime(new Date().getTime());
            return object;
        }
    }

    class RecycliingThread implements Runnable{

        private Queue<ESClient> usingPool;

        private Queue<ESClient> pool;

        public RecycliingThread (Queue<ESClient> pool,Queue<ESClient> usingPool){
            this.pool = pool;
            this.usingPool = usingPool;
        }

        @Override
        public void run() {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("****************开始进行连接池回收*********************");
                if (usingPool != null && usingPool.size() > 0){
                    int size = usingPool.size();

                    for (int i = 0;i < size;i ++){
                        ESClient client = usingPool.poll();
                        long lastUsedTime = client.getLastUsedTime();
                        long nowTime = new Date().getTime();
                        long time = (nowTime - lastUsedTime) / 1000;
                        if (time >= idleTime){
                            Map<Thread,ESClient> map = threadLocal.getMap();
                            for (Map.Entry<Thread,ESClient> entry: map.entrySet()){
                                if (map.values().contains(client)){
                                    map.remove(entry.getKey());
                                }
                            }
                            //TODO 这里进行验证
                            pool.add(client);
                        }else {
                            usingPool.add(client);
                        }
                    }
                }
                System.out.println("****************本次连接池回收结束*********************");
            }
        }
    }
    /**
     * 连接池初始化
     */
    private void init(){
        System.out.println("开始初始化");
        try {
            if (hostname != null){
                for (int i = 0;i < initSize;i++){
                    ESClient tmp = ESTransfortClient.ESClientBuilder.init(settings, hostname, port);
                    ESClient client = new ESClientProxy().getESClientProxy(tmp);
                    pool.add(client);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public ESClient getClient() {
        ESClient client = threadLocal.get();
        try {
            if (client == null){
                client = pool.poll(10, TimeUnit.SECONDS);
                if (client == null){
                    if (currentSize < maximun){
                        ESClient tmp = ESTransfortClient.ESClientBuilder.init(settings, hostname, port);
                        client = new ESClientProxy().getESClientProxy(tmp);
                        currentSize ++;
                    }else {
                        int i = 0;
                        while (i < 2){
                            client = pool.poll(10, TimeUnit.SECONDS);
                        }
                        if (client == null){
                            throw new Exception("尝试3次获取连接，没有空闲的连接可使用，请重新尝试获取连接");
                        }
                    }
                }
                threadLocal.set(client);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        usingPool.add(client);
        return client;
    }



    public int getMinimun() {
        return minimun;
    }

    public void setMinimun(int minimun) {
        this.minimun = minimun;
    }

    public int getMaximun() {
        return maximun;
    }

    public void setMaximun(int maximun) {
        this.maximun = maximun;
    }

    public int getInitSize() {
        return initSize;
    }

    public void setInitSize(int initSize) {
        this.initSize = initSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Map<String,Object> settings) {
        this.settings = ESTransfortClient.getSettings(settings);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
