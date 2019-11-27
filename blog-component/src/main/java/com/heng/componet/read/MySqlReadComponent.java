package com.heng.componet.read;

import com.alibaba.druid.pool.DruidDataSource;
import com.heng.blog_system.utils.MapUtils;
import com.heng.common.impl.Component;
import com.heng.common.bean.ParamObject;
import com.heng.common.util.DataSourceManager;
import com.heng.common.util.PropertyUtil;

import javax.sql.DataSource;

public class MySqlReadComponent extends Component {
    @Override
    public Object process(ParamObject paramObjects) {
        String driver = MapUtils.getString(paramObjects,"dirver");
        String url = MapUtils.getString(paramObjects, "url");
        String user = MapUtils.getString(paramObjects, "user");
        String password = MapUtils.getString(paramObjects, "password");
        String table = MapUtils.getString(paramObjects, "table");
        String key = driver+url+user+password;
        DataSource dataSource = DataSourceManager.get(key);
        if (dataSource == null){
            DruidDataSource dds = new DruidDataSource();
            dds.setDriverClassName(driver);
            dds.setUrl(url);
            dds.setUsername(user);
            dds.setPassword(password);
            dds.setMaxActive(Integer.parseInt(PropertyUtil.getPropertyUtil("conf.properties").getProperty("datasource.maxActive")));
            dds.setInitialSize(Integer.parseInt(PropertyUtil.getPropertyUtil("conf.properties").getProperty("datasource.initialSize")));
            dds.setMinIdle(Integer.parseInt(PropertyUtil.getPropertyUtil("conf.properties").getProperty("datasource.minIdle")));
            dds.setPoolPreparedStatements(Boolean.parseBoolean(PropertyUtil.getPropertyUtil("conf.properties").getProperty("datasource.poolPreparedStatements")));
            DataSourceManager.put(key,dds);
        }

        return null;
    }
}
