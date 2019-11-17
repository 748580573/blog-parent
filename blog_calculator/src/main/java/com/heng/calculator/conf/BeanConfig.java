package com.heng.calculator.conf;

import com.heng.calculator.utils.ESClientPool;
import com.heng.common.util.PropertyUtil;
import com.heng.util.ESTransfortClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfig {

    @Bean
    public PropertyUtil propertyUtil(){
        return PropertyUtil.getPropertyUtil("config.properties");
    }

    @Bean
    public ESClientPool esClientPool(){
        PropertyUtil util = PropertyUtil.getPropertyUtil("config.properties");
        String hostname = util.getProperty("es.host");
        String port = util.getProperty("es.port");
        Map<String,Object> param = new HashMap<>();
        param.put("cluster.name", util.getProperty("cluster.name"));
        ESClientPool esClientPool = new ESClientPool();
        esClientPool.setSettings(param);
        esClientPool.setHostname(hostname);
        esClientPool.setPort(Integer.valueOf(port));
        return esClientPool;
    }
}
