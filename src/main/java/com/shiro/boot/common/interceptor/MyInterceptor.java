package com.shiro.boot.common.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 *
 * SpringWebMVC的处理器--拦截器，类似于Servlet开发中的过滤器，用于处理器进行预处理和后处理。
 * 应用场景：
 *      1.日志记录：可以记录请求信息的日志，以便进行信息监控，信息统计等；
 *      2.权限检查：如登录检测，进入处理器检测是否登录，如果没有直接返回登录界面；
 *      3.性能监控：典型的是慢日志。
 *
 * @author jzl
 * @date 2020/3/23 9:08
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 预处理回调方法，实现处理器的预处理（如检查登录），第三个参数为响应的处理器，自定义Controller
     *
     * @param request
     * @param response
     * @param handler
     * @return 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他
     *                  的拦截器或处理器，此时我们需要通过response来产生响应。
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI()+" 方法开始执行了");
        return true;
    }

    /**
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 整个请求完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在这里记录请求结束时间并输出消耗时间，还可以进行一些
     * 资源清理，类似于 try-catch-finally 中的 finally
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("方法执行结束啦");
    }
}
