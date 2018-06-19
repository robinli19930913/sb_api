package com.cn.lxg.web.apiversion;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Created by likun on 2017/4/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
//版本控制  根据方法配置的版本号替换这个值  如果请求的版本5没有对应的版本会向下兼容 找4 再找3  再找2 且保证不会跳过4和3 找到2
public @interface ApiVersion {
    String value();
}