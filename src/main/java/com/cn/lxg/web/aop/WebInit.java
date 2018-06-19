package com.cn.lxg.web.aop;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * @author guozijian
 * @date 2017/4/25 19:36
 */
@Component
public class WebInit implements ServletContextListener {
    private static final Log log = LogFactory.getLog(WebInit.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.error("项目成功启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.error("项目关闭");
    }

    public static void main(String[] args) {
        System.out.println(11);
    }
}
