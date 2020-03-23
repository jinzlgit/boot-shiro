package com.shiro.boot.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 自定义 监听器
 * @author jzl
 * @date 2020/3/23 13:42
 */
@Slf4j
@WebListener
public class MyListener implements ServletContextListener {
    /**
     * 当 Servlet 容器启动 Web 应用时调用该方法。在调用完该方法后，容器再对 Filter 初始化。
     * 并且对那些在 Web 应用启动时就需要被初始化的 Servlet 进行初始化
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("监听器开始初始化");
    }

    /**
     * 当 Servlet 容器终止 Web 应用时调用该方法。在调用该方法前，容器会先销毁所有的 Servlet 和 FIlter 过滤器
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("监听器开始销毁");
    }
}
