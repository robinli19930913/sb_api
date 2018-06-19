package com.cn.lxg.web.apiversion;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Created by likun on 2017/4/20.
 */
public class ApiVersionMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<ApiVesrsionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVesrsionCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    private RequestCondition<ApiVesrsionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVesrsionCondition(apiVersion.value());
    }
}

