package com.cn.lxg;

import com.cn.lxg.web.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({"com.cn.lxg.web"})
@Import({WebMvcConfig.class})
public class StartMain {

	public static void main(String[] args) {
		System.out.println("启动开始");
		SpringApplication.run(StartMain.class, args);
		System.out.println("启动成功！");
	}
}
