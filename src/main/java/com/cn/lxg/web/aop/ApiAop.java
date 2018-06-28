package com.cn.lxg.web.aop;


import com.alibaba.fastjson.JSONObject;
import com.cn.lxg.web.config.CategoryLog;
import com.cn.lxg.web.config.DescLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 权限验证/登录验证
 */
public class ApiAop implements HandlerInterceptor {
    private static final Log log = LogFactory.getLog(ApiAop.class);
    @Resource
    Environment environment;

//    @Resource
//    RedisClient redisClient;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        System.out.println("preHandle:"+response.getStatus());

        /*获取类上面的注解*/
        Class<?> controller = handlerMethod.getBean().getClass();
        CategoryLog categoryLog = AnnotationUtils.findAnnotation(controller,CategoryLog.class);
        if(categoryLog != null){
            String[] menu = categoryLog.menus();
            System.out.println("类的栏目:"+JSONObject.toJSONString(menu));
        }

        /*获取方法上面的注解*/
        Method method = handlerMethod.getMethod();
        LoginAuth loginAuth = AnnotationUtils.findAnnotation(method,LoginAuth.class);
        if(loginAuth!=null)//判断是否需要做登录权限验证
        {
            System.out.println("登录校验");
        }

        DescLog descLog = AnnotationUtils.findAnnotation(method,DescLog.class);
        if(descLog != null){
            String value = descLog.value();
            System.out.println("方法功能："+value);
        }

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle:"+response.getStatus());
    }
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("afterCompletion:"+response.getStatus());
    }

    public void printBody(HttpServletResponse response,Object o){
        ServletOutputStream servletOutputStream = null;
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            servletOutputStream = response.getOutputStream();
            servletOutputStream.write(JSONObject.toJSONString(o).getBytes("UTF-8"));
        } catch (IOException e) {
            log.error("",e);
        }
        finally {
            if(servletOutputStream!=null) try {
                servletOutputStream.close();
            } catch (IOException e) {
                log.error("",e);
            }
        }
    }
}
