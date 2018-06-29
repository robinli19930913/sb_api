package com.cn.lxg.web.test;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by robin on 2018/6/29.
 */
@Service
public class ThreadTestService {
    @Async("complexAsyncExecutorVisiable")
    public void doTask1() throws InterruptedException{
        System.out.println("Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(10000);
        long end = System.currentTimeMillis();

        System.out.println("Task1 finished, time elapsed:" + (end-start));
    }

    @Async("complexAsyncExecutorVisiable")
    public void doTask2() throws InterruptedException{
        System.out.println("Task2 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();

        System.out.println("Task2 finished, time elapsed:"+ (end-start));

    }
}
