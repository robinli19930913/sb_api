package com.cn.lxg;

import com.cn.lxg.web.config.WebMvcConfig;
import com.cn.lxg.web.config.redis.JedisConfig;
import com.cn.lxg.web.config.redis.JedisProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({"com.cn.lxg.web"})
@MapperScan(basePackages = {"com.cn.lxg.web.dao.mapper"})
@Import({WebMvcConfig.class,JedisConfig.class})
public class StartMain {

	public static void main(String[] args) {
		System.out.println("启动开始");
		SpringApplication.run(StartMain.class, args);
		System.out.println("启动成功！");
	}
}
