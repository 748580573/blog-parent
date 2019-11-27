package com.heng.calculator.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectThreadLocal<T> {

    private Map<Thread,T> map = new ConcurrentHashMap<>();

    public void set(T t){
        Thread thread = Thread.currentThread();
        map.put(thread, t);
    }

    public T get(){
        Thread thread = Thread.currentThread();
        return map.get(thread);
    }

    public Map<Thread,T> getMap(){
        return map;
    }
}
