package com.heng.calculator.service.impl;

import com.heng.calculator.DB.ESDAO;
import com.heng.calculator.constant.SystemConstant;
import com.heng.calculator.service.ESService;
import com.heng.common.bean.RestResult;
import com.heng.common.bean.TransferObject;
import com.heng.common.util.PropertyUtil;
import com.heng.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ESServiceImpl implements ESService {

    @Autowired
    private PropertyUtil propertyUtil;

    @Autowired
    private ESDAO esdao;

    @Override
    public RestResult insert(TransferObject object) {
        RestResult restResult = new RestResult();
        String json = object.getJson();
        String clazzString = object.getClazz();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(clazzString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object bean = Utils.jsonToObject(json, clazz);
        if (esdao.insert(bean)){
            restResult.setStauts(SystemConstant.STATU_SUCCESS);
            restResult.setMessage(SystemConstant.MESSAGE_SUCESS);
        }
        restResult.setStauts(SystemConstant.STATU_FAILE);
        restResult.setMessage(SystemConstant.MESSAGE_FAILE);
        return restResult;
    }

    @Override
    public<T> List<T> select(Map<String, Object> search, Class<T> clazz) {
        List<T> result = null;
        String indexName = propertyUtil.getProperty("es.index");
        result = esdao.select(search,indexName,clazz);
        return result;
    }
}
