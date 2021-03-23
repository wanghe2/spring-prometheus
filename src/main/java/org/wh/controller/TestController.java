package org.wh.controller;

import io.micrometer.core.instrument.Tags;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wh.service.MyAsyncService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private PrometheusMeterRegistry prometheusMeterRegistry;

    @Autowired
    private MyAsyncService asyncService;

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


    @Async
    @GetMapping("asyncFun")
    public void asyncFun() throws InterruptedException {
        Thread.sleep(1000*30);
        System.err.println("----------请求完成--------");
    }

    @GetMapping("syncFun")
    public void syncFun() throws InterruptedException {
        Thread.sleep(1000*30);
        System.err.println("##########请求完成#########");
    }

    @Async
    @GetMapping("asyncFunWithBackRes")
    public String asyncFunBackResult(int i){
        try {
            // 这个方法需要调用20秒
            Thread.sleep(1000*20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "返回结果";
    }

    @GetMapping("asyncFunWithBackRes1")
    public String asyncFunBackResult1(int i) throws ExecutionException, InterruptedException {
        Future<String> future = asyncService.doReturn(i);
//        future.get();
        return "异步任务已在执行中";
    }

    @GetMapping("/hi")
    public Map<String, Object> testAsyncReturn() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<String> future = asyncService.doReturn(i);
            futures.add(future);
        }
        List<String> response = new ArrayList<>();
        for (Future future : futures) {
            String string = (String) future.get();
            response.add(string);
        }
        map.put("data", response);
        map.put("消耗时间", String.format("任务执行成功,耗时{%s}毫秒", System.currentTimeMillis() - start));
        return map;

    }



}
