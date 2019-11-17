package com.heng.calculator.service;

import com.heng.common.bean.RestResult;
import com.heng.common.bean.TransferObject;

import java.util.List;
import java.util.Map;

public interface ESService {

    RestResult insert (TransferObject object);

    public<T> List<T> select(Map<String, Object> search, Class<T> clazz);
}
