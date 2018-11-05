package com.cn.lxg.web.config;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public  @interface AEC {
    /**
     * 操作名称
     */
    String value();
}
