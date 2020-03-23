package com.shiro.boot.common.config;


import com.shiro.boot.common.filter.MyFilter;
import com.shiro.boot.common.interceptor.MyInterceptor;
import com.shiro.boot.common.listener.MyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 在这里注册Servlet Filter Listener 或者使用 @ServletComponentScan
 *
 * @author jzl
 * @date 2020/3/23 9:47
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    /**
     *  这个方法用来注册拦截器，自定义拦截器需要通过这里添加注册才能生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/**");
    }

    /**
     * 注册 过滤器
     * @return
     */
//    @Bean
//    public FilterRegistrationBean filterRegist() {
//        FilterRegistrationBean frBean = new FilterRegistrationBean();
//        frBean.setFilter(new MyFilter());
//        return frBean;
//    }

    /**
     * 注册 监听器
     * @return
     */
//    @Bean
//    public ServletListenerRegistrationBean listenerRegist() {
//        ServletListenerRegistrationBean listenerBean = new ServletListenerRegistrationBean();
//        listenerBean.setListener(new MyListener());
//        return listenerBean;
//    }
}
