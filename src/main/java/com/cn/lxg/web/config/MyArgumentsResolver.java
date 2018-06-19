package com.cn.lxg.web.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Iterator;

public class MyArgumentsResolver implements HandlerMethodArgumentResolver {
    /**
     * 解析器是否支持当前参数
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 指定参数如果被应用MyParam注解，则使用该解析器。
        // 如果直接返回true，则代表将此解析器用于所有参数
        return "data".equals(parameter.getParameterName())||
                parameter.hasParameterAnnotation(JsonParam.class);
    }

    /**
     * 需要循环参数 判断，因为会忽略大小写
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            Iterator<String>  iterator = webRequest.getParameterNames();
            while (iterator.hasNext())
            {
                String param = iterator.next();
                if(param.toLowerCase().equals(parameter.getParameterName().toLowerCase()))
                {
                    if(webRequest.getParameter(param)!=null)
                    {
                        return JSON.toJavaObject(JSONObject.parseObject(webRequest.getParameter(param))
                                ,parameter.getParameterType());
                    }
                }
            }
        return null;
    }
}