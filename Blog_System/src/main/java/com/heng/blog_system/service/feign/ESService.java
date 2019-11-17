package com.heng.blog_system.service.feign;


import com.heng.common.bean.TransferObject;
import com.heng.common.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "blogCalculator")
@Component
public interface ESService {

    @RequestMapping(value = "/es/select",method = RequestMethod.POST,consumes = "application/json")
    List<User> test(@RequestBody TransferObject object);

}
