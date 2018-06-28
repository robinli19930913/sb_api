package com.cn.lxg.web.test;

import com.cn.lxg.web.config.CategoryLog;
import com.cn.lxg.web.config.DescLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by robin on 2018/6/28.
 */
@RestController
@RequestMapping("log1/")
@CategoryLog(menus = "栏目1")
public class Log1 {
    @RequestMapping("test")
    @DescLog(value = "测试功能")
    public String test(){
        return "日志测试成功";
    }
}

