package com.cn.lxg.web.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @Description TODO
 * @Author robin
 * @Date 2018/12/26 10:29
 */
public interface ContrabandMapperExt {
    @Select({"<script>select key_word from contraband where active = 1 " +
            "<if test = 'type != null'>" +
            "   and type = #{type} " +
            "</if>" +
            "</script>"})
    Set<String> selectKeyWordsByType(@Param("type") Integer type);
}
