package com.cn.lxg.web.config;

import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.cn.lxg.web.aop.ApiAop;
import com.cn.lxg.web.apiversion.ApiVersionMappingHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by likun on 2017/4/20.
 */
@Configuration
public class WebMvcConfig extends  WebMvcConfigurationSupport{
    /**
     * 增加版本控制
     * @return
     */
    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new ApiVersionMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }

    @Bean
    public ApiAop apiAop() {
        return new ApiAop();
    }

    /**
     * 设置拦截器
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiAop()).addPathPatterns("/**");
    }
//    protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer() {
//        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
//        initializer.setConversionService(this.mvcConversionService());
//        initializer.setValidator(this.mvcValidator());
//        initializer.setMessageCodesResolver(this.getMessageCodesResolver());
//        return initializer;
//    }
//
//    /**
//     * 设置url不区分大小写
//     * @param configurer
//     */
//    public void configurePathMatch(PathMatchConfigurer configurer){
//        AntPathMatcher pathMatcher = new AntPathMatcher();
//        pathMatcher.setCaseSensitive(false);
//
//        configurer.setPathMatcher(pathMatcher);
//    }

    /**
     * json数据的解析规则
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        NameFilter nameFilter = new NameFilter() {
            @Override
            public String process(Object o, String s, Object o1) {
//                System.out.println(o+":"+s+":"+o1);
                return s.toLowerCase();
            }
        };
        ValueFilter valueFilter = new ValueFilter() {
            @Override
            public Object process(Object o, String s, Object o1) {
//                System.out.println(o+":"+s+":"+o1);
                return o1;
            }
        };

        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        fastJsonConfig.setSerializeFilters(nameFilter,valueFilter);

        converters.add(fastConverter);
//        converters.add(customJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);



    }

    /**
     * 扩展参数的绑定
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new MyArgumentsResolver());
    }

}
