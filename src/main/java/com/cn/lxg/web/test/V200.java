package com.cn.lxg.web.test;

import com.cn.lxg.web.apiversion.ApiVersion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by robin on 2018/6/19.
 */
@RestController
@RequestMapping("test/")
@ApiVersion("2.0.0")
public class V200 {
    @RequestMapping("t")
    public String t(){
        return "2.0.0";
    }
}
