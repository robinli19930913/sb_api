package com.cn.lxg.web.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ResourceBundle;

/**
 * Created by robin on 2018/6/19.
 */
public class Config {
    private static final Log log = LogFactory.getLog(Config.class);
    private static ResourceBundle bundle;

    static {
        try {
            bundle = ResourceBundle.getBundle("config");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

    public static void main(String[] args) {
        String s = Config.get("test.name");
        System.out.println(s);
    }
}
