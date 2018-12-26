package com.cn.lxg.web.test.mingganciguolv;

import com.cn.lxg.web.config.redis.RedisClient;
import com.cn.lxg.web.dao.mapper.ContrabandMapper;
import com.cn.lxg.web.dao.mapper.ContrabandMapperExt;
import com.cn.lxg.web.dao.model.Contraband;
import com.cn.lxg.web.dao.model.ContrabandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Description TODO
 * @Author robin
 * @Date 2018/12/25 18:47
 */
@Service
public class GuoLvService {
    @Resource
    private ContrabandMapper contrabandMapper;
    @Resource
    private RedisClient redisClient;
    @Resource
    private ContrabandMapperExt contrabandMapperExt;

    public List<Contraband> getList() {
        ContrabandExample contrabandExample = new ContrabandExample();
        return contrabandMapper.selectByExample(contrabandExample);
    }

    public Set<String> getStrList() {
        return contrabandMapperExt.selectKeyWordsByType(null);
    }

    public Set<String> getRdisStrList() {
        String key = "contraband:keys";
        JedisPool jedisPool = redisClient.getJedisPool();
        Jedis resource = jedisPool.getResource();
        Set<String> set = resource.sunion(key);
        if(set == null || set.isEmpty()){
            set = contrabandMapperExt.selectKeyWordsByType(null);
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()){
                resource.sadd(key,iterator.next());
            }
        }
        return set;
    }

    public List<Contraband> getLevel(Set<String> sensitiveWord) {
        ContrabandExample contrabandExample = new ContrabandExample();
        contrabandExample.createCriteria()
                .andActiveEqualTo(Short.valueOf("1"))
                .andKeyWordIn(new ArrayList<>(sensitiveWord));
        List<Contraband> contrabands = contrabandMapper.selectByExample(contrabandExample);
        contrabands.forEach(n ->{
            if(n.getType().shortValue() > 0){
                n.setLevel(Short.valueOf("1"));
                n.setActive(null);
            }
        });
        return contrabands;
    }
}
