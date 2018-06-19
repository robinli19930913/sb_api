package com.cn.lxg.web.aop;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by libin on 2016/10/20.
 */


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAuth {
    /**
     * 可以访问的用户角色 如果为null则全部都可以访问
     * @return
     */
    String[] usertype() default {};

    /**
     * 可以访问的用户权限key 如果为null 则全部都可以访问
     * @return
     */
    String[] pkey() default {};
}
