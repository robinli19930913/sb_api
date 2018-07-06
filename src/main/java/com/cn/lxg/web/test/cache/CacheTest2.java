package com.cn.lxg.web.test.cache;

/**
 * Created by robin on 2018/7/5.
 */
public class CacheTest2 {
    public static void main(String[] args) {
        //创建一个工厂，暂时不支持持久化
        GroupCacheFactory factory=new GroupCacheFactory();
        //获取一个组
        Group group1=factory.group("group1");
        System.out.println(group1.getValue("d"));
    }
}
