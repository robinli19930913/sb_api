package com.cn.lxg.web.config;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Created by robin on 2018/6/28.
 * <p>用于记录系统操作日志的注解 -- 方法注解</p>
 * <p>在需要记录系统操作日志的Controller中待记录系统操作日志的方法上添加该注解。</p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public  @interface DescLog {
    /**
     * 操作名称
     */
    String value();
}
