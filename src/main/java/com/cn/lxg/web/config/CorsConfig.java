package com.cn.lxg.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by likun on 2017/4/28.
 * 实现跨域请求
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}

//测试代码
//$.ajax({
//        type: "get",
//        url: "http://localhost:8080/user/test/?v=4.2.0",
//        dataType: 'json',
//        contentType:"application/json",
//        data: {"test2Pv":JSON.stringify({"v":"4.2.0","name":"123", "testpv":{"clientpv":{"deviceid":"1"}}})},
//        success: function (data) {
//        alert(JSON.stringify(data));
//        }
//        });