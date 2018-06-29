package com.cn.lxg.web.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by robin on 2018/6/29.
 * 项目启动的时候创建线程池
 * 线程池配置文件
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    /** 设置线程池执行器的最小池大小。 */
    private int corePoolSize = 1;
    /** 设置线程池执行器的最大池大小。 */
    private int maxPoolSize = 5;
    /** 设置线程池执行器的阻塞队列的容量。 */
    private int queueCapacity = 10;

    /**
     * 简单引用
     * 用的时候打开注释
     * @return
     */
//    @Bean
//    public Executor simpleAsyncExecutor() {
//        System.out.println("simpleAsyncExecutor");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(corePoolSize);
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setQueueCapacity(queueCapacity);
//        executor.setThreadNamePrefix("SimpleAsyncExecutor");
//        executor.initialize();
//        return executor;
//    }

    /**
     * 简单引用
     * @return
     */
//    @Bean
//    public Executor simpleAsyncExecutorVisiable() {
//        System.out.println("simpleAsyncExecutorVisiable");
//        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
//        executor.setCorePoolSize(corePoolSize);
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setQueueCapacity(queueCapacity);
//        executor.setThreadNamePrefix("SimpleAsyncExecutor");
//        executor.initialize();
//        return executor;
//    }

    /**
     * 复杂引用（推荐）
     * 重新设置了pool已经达到max size时候的处理方法；同时指定了线程名字的前缀。
     * @return
     */
//    @Bean
//    public Executor complexAsyncExecutor() {
//        System.out.println("complexAsyncExecutor");
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(corePoolSize);
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setQueueCapacity(queueCapacity);
//        executor.setThreadNamePrefix("ComplexAsyncExecutor");
//
//        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
//        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.initialize();
//        return executor;
//    }

    /**
     * 复杂引用（推荐）
     * 重新设置了pool已经达到max size时候的处理方法；同时指定了线程名字的前缀。
     * 直接在使用异步的方法上面添加 @Async("complexAsyncExecutorVisiable")
     * @return
     */
    @Bean
    public Executor complexAsyncExecutorVisiable() {
        System.out.println("complexAsyncExecutorVisiable");
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("ComplexAsyncExecutor");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
