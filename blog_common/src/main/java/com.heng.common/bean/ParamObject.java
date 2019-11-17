package com.heng.common.bean;

import java.util.HashMap;

public class ParamObject extends HashMap<String,Object> {

    public ParamObject(){
        super();
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (Entry<String,Object> entry : entrySet()){
            sb.append("{");
            sb.append(entry.getKey());
            sb.append(",");
            sb.append(entry.getValue());
            sb.append("}");
        }
        sb.append("]");
        return sb.toString();
    }
}
