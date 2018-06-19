package com.cn.lxg.web.config;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Created by likun on 2017/4/20.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
/**
 * json 参数表示，如果是有这个注解的或者是data参数的 会自动用json做转换
 */
public @interface JsonParam {
    boolean required() default false;
}