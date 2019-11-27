package com.heng.calculator.controller;

import com.alibaba.fastjson.JSONObject;
import com.heng.calculator.service.ESService;
import com.heng.common.bean.RestResult;
import com.heng.common.bean.TransferObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/es")
public class ESController {

    @Autowired
    ESService esService;

    @RequestMapping("/select")
    public RestResult insertES(HttpServletRequest request, @RequestBody TransferObject transferObject) throws ClassNotFoundException {

        RestResult restResult = new RestResult();
        String json = transferObject.getJson();
        JSONObject object = JSONObject.parseObject(json);
        Map<String,Object>  search = (Map<String, Object>) object.get("search");
        String clazzStr = object.getString("clazz");
        Class<?> clazz = Class.forName(clazzStr);
        List list = esService.select(search, clazz);
        restResult.setData(list);
        return restResult;
    }
}
