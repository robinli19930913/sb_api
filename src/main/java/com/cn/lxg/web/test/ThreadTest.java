package com.cn.lxg.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by robin on 2018/6/29.
 * 注意！使用的时候要利用service 这样引用 直接调用方法的话，不起作用
 * 理解是：启动现成的注解没有生效
 */
@RestController
@RequestMapping("thread/")
public class ThreadTest {
    @Resource
    ThreadTestService threadTestService;

    @RequestMapping("test")
    public void test(){
        System.out.println("开始");
        try {
            threadTestService.doTask1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }

    @RequestMapping("test1")
    public void test1(){
        System.out.println("开始");
        try {
            threadTestService.doTask2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束------");
    }


}
