package org.wh.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.wh.service.MyAsyncService;


import java.util.concurrent.Future;

@Service
public class MyAsyncServiceImpl implements MyAsyncService {

    @Override
    @Async
    public Future<String> doReturn(int i) {
        try {
            // 这个方法需要调用20秒
            Thread.sleep(1000*20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 消息汇总
        return new AsyncResult<>(String.format("这个是第{%s}个异步调用,带有返回数据", i));
    }
}
