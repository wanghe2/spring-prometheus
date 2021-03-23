package org.wh.service;

import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

public interface MyAsyncService {
    Future<String> doReturn(int i);
}
