package com.heng.common.bean;

import com.heng.common.util.Utils;

import java.io.Serializable;

public class TransferObject implements Serializable {

    private String json;          //对象的序列话的json

    private String clazz;         //对象实际的类型

    public TransferObject(){};

    public TransferObject(String json,String clazz){
        this.clazz = clazz;
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public static TransferObject getTransferObject(Object object){
        String json = Utils.objectToJson(object);
        String clazz = object.getClass().getName();
        return new TransferObject(json, clazz);
    }
}
