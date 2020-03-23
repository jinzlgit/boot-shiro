package com.shiro.boot.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 使用AOP记录 访问日志
 * 使用 @Before 在切入点开始处切入内容
 * 使用 @After 在切入点结尾处切入内容
 * 使用 @AfterReturning 在切入点 return 内容之后切入内容（可以用来对处理返回值做一些加工处理）
 * 使用 @Around 在切入点前后切入内容，并自己控制何时执行切入点自身的内容
 * 使用 @AfterThrowing 用来处理当切入内容部分抛出异常之后的处理逻辑
 *
 * 注解：
 *      Aspect：AOP
 *      Component：Bean
 *      Slf4j：可以直接使用 log 输出日志
 *      Order：多个AOP切同一个方法时的优先级，越小优先级越高越大
 *              在切入点之前的操作：按 order 的值由小到大执行
 *              在切入点之后的操作：按 order 的值由大到小执行
 *
 * @author jzl
 * @date 2020/3/23 14:02
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect {

    /**
     * 线程存放信息
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义切入点
     * 第一个 * ：标识所有返回类型
     * 字母路径：包路径
     * 两个点 .. ：当前包以及子包
     * 第二个 * ：所有的类
     * 第三个 * ：所有的方法
     * 最后的两个点：所有的类型参数
     */
    @Pointcut("execution(public * com.shiro.boot.controller..*.*(..))")
    public void webLog() {

    }

    /**
     * 在切入点开始处切入内容
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 记录请求时间
        startTime.set(System.currentTimeMillis());
        // 获取请求域
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 记录请求内容
        log.info("LogAspect-URL: " + request.getRequestURI().toLowerCase());
        log.info("LogAspect-HTTP_METHOD: " + request.getMethod());
        log.info("LogAspect-IP: " + request.getRemoteAddr());
        log.info("LogAspect-REQUEST_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() +
                "." + joinPoint.getSignature().getName());
        log.info("LogAspect-Args: " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 在切入点之后切入内容
     */
    @After("webLog()")
    public void doAfter() {

    }

    /**
     * 在切入点 return 内容之后切入内容（可以用来对对处理返回值做一些加工处理）
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning (Object ret) {
        log.info("LogAspect-Response: " + ret);
        Long endTime = System.currentTimeMillis();
        log.info("LogAspect-SpeedTime: " + (endTime - startTime.get()) + "ms");
    }

}
