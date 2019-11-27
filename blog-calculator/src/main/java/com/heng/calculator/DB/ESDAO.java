package com.heng.calculator.DB;

import com.heng.calculator.constant.SystemConstant;
import com.heng.calculator.utils.ESClientPool;
import com.heng.common.util.PropertyUtil;
import com.heng.common.util.Utils;
import com.heng.util.ESClient;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class ESDAO {

    @Autowired
    private ESClientPool esClientPool;

    public  boolean insert(Object object){

        return false;
    }

    public boolean delete(Object object,String id){
        return true;
    }

    public <T> List<T> select(Map<String,Object> search,String indexName,Class<T> clazz){
        List<T> result = new ArrayList<>();
        ESClient client = esClientPool.getClient();
        List<String> list = client.getDataByShouldQuery(indexName, clazz.getSimpleName(), search);
        if (list != null){
            for (String str : list){
                T res = Utils.jsonToObject(str, clazz);
                result.add(res);
            }
        }
        return result;
    }

    public boolean update(String id,Map<String,Object> field,Class<?> clazz){

        return false;
    }
}
