package com.cn.lxg.web.test;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by robin on 2018/10/19.
 */
public class Demo {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new Hashtable<>();
        for(int i = 1;i<10000000;i++){
            map.put(i,i);
        }
        long start = System.currentTimeMillis();
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, Integer> next = iterator.next();
            Integer value = next.getValue();
            Integer key = next.getKey();
            System.out.print(key+" ");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println();
        start = System.currentTimeMillis();
        for(Map.Entry<Integer, Integer> next : map.entrySet()){
            Integer value = next.getValue();
            Integer key = next.getKey();
            System.out.print(key+" ");
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
