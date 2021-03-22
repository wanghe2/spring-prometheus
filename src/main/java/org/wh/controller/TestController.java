package org.wh.controller;

import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private PrometheusMeterRegistry prometheusMeterRegistry;

    @GetMapping("fun1")
    public String fun1(){
        return "测试内容1";
    }

    @GetMapping("roleData")
    public String getRoleList(String role){
        if(StringUtils.isEmpty(role)){
            role = "defaultRole";
        }
        Tags tags = Tags.of("roleRequest",role);
        prometheusMeterRegistry.counter("roleRecords",tags).increment();
        return "查询角色，并加入监测统计中";
    }


    @GetMapping("userData")
    public String getUserList(String user){
        if(StringUtils.isEmpty(user)){
            user = "defaultUser";
        }
        Tags tags = Tags.of("userRequest",user);
        prometheusMeterRegistry.counter("userRecords",tags).increment();
        return "查询用户，并加入监测统计中";
    }


}
