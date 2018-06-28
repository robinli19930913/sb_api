package com.cn.lxg.web.config;

import java.lang.annotation.*;

import org.springframework.web.bind.annotation.Mapping;

/**
 * Created by robin on 2018/6/28.
 * <p>用于记录系统操作日志的注解 -- 类注解 </p>
 * <p>在需要记录系统操作日志的Controller上添加该注解</p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface CategoryLog {
    /**
     * 菜单名
     */
    String[] menus() default {};

    /**
     * 表名
     */
    String[] tables() default {};
}
