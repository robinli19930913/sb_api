package com.cn.lxg.web.test;

import com.cn.lxg.web.aop.LoginAuth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by robin on 2018/6/19.
 */
@RestController
@RequestMapping("login/")
public class login {
    @RequestMapping("l")
    @LoginAuth
    public String t(){
        return "登录校验";
    }
}
