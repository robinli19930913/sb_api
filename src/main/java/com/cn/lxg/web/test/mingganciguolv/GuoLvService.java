package com.cn.lxg.web.test.mingganciguolv;

import com.cn.lxg.web.dao.mapper.ContrabandMapper;
import com.cn.lxg.web.dao.model.Contraband;
import com.cn.lxg.web.dao.model.ContrabandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author robin
 * @Date 2018/12/25 18:47
 */
@Service
public class GuoLvService {
    @Autowired
    private ContrabandMapper contrabandMapper;

    public List<Contraband> getList() {
        ContrabandExample contrabandExample = new ContrabandExample();
        return contrabandMapper.selectByExample(contrabandExample);
    }
}
