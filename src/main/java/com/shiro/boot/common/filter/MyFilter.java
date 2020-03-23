package com.shiro.boot.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * 过滤器是对数据进行过滤，预处理过程，当我们访问网站时，有时候会发布一些敏感信息，发完以后有的会用 * 代替，还有就是登录权限
 * 控制等，其它功能：实现URL级别的权限控制，压缩响应信息，编码格式等。
 * 过滤器依赖 Servlet 容器，在实现上基于函数回调，可以对几乎所有请求进行过滤。
 *
 * WebFilter 此注解表示这个类是 过滤器
 * filterName: 过滤器名字；urlPatterns: 过滤器的范围；initParams: 过滤器初始化参数
 *
 * @author jzl
 * @date 2020/3/23 11:29
 */
@Slf4j
@WebFilter(filterName = "myFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "URL", value = "boot.shiro.com")
})
public class MyFilter implements Filter {
    /**
     * 主要的业务代码编写方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("我是过滤器的执行方法，客户端向Servlet发送的请求被我拦截到了");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("我是过滤器的执行方法，Servlet向客户端发送的响应被我拦截到了");
    }

    /**
     * 可以初始化 Filter 在 web.xml 里面配置的初始化参数
     * filter 对象只会创建一次，init 方法也只会执行一次
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 获取初始化参数的值
        String url = filterConfig.getInitParameter("URL");
        log.info("过滤器初始化! URL=" + url + ",生活开始......");
    }

    /**
     * 在销毁 filter 时自动调用
     */
    @Override
    public void destroy() {
        log.info("我是过滤器的被销毁时调用的方法！活不下去了......");
    }
}
