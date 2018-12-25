package com.cn.lxg.web.test.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.geo.GeoRadiusParam;

/**
 * Created by robin on 2018/11/19.
 */
public class geo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379); // 默认端口
        Jedis jedis1 = new Jedis("127.0.0.1",6380); // 默认端口
        Jedis jedis2 = new Jedis("127.0.0.1",6381); // 默认端口
        String key = "diliweizhi:j:w:id";
        System.out.println(jedis.ttl(key));
        System.out.println(JSON.toJSONString(jedis1.geopos(key,"yanan")));
        jedis.set("test","1242141");
        System.out.println(jedis1.get("test"));
        System.out.println(jedis2.get("test"));
//        jedis.geoadd(key,116.397128,39.916527,"beijing1");//设置经纬度
//        jedis.geoadd(key,116.36914719555662,39.918633541343965,"beijing2");
//        jedis.geoadd(key,116.312842263916,39.89045318862986,"beijing3");
//        jedis.geoadd(key,116.3461445710449,39.833003153663824,"beijing4");
//        jedis.geoadd(key,109.4932507416992,36.31257658221882,"yanan");
//        Double geodist = jedis.geodist(key, "yanan", "beijing1", GeoUnit.KM);//获取距离
//        System.out.println(JSON.toJSONString(jedis.geopos(key,"yanan")));//获取经纬度
//        System.out.println(geodist);
//        System.out.println(JSON.toJSONString(jedis.georadius(key,116.397128,39.916527,1,GeoUnit.KM, GeoRadiusParam.geoRadiusParam().sortAscending())));//获取附近地址
    }
}
