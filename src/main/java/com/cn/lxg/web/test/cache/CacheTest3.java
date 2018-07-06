package com.cn.lxg.web.test.cache;

import com.cn.lxg.web.config.CategoryLog;
import com.cn.lxg.web.config.DescLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by robin on 2018/6/28.
 */
@RestController
@RequestMapping("cache/")
public class CacheTest3 {
    @Resource
    GroupCacheFactory groupCacheFactory;

    @RequestMapping("t")
    public String test1(){
        Group test = groupCacheFactory.group("test");
        test.push("a","123",300);
        return "成功！";
    }

    @RequestMapping("t1")
    public String test2(){
        Group test = groupCacheFactory.group("test");
        System.out.println(test.getValue("a"));
        return "成功！";
    }
}

