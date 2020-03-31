package com.shiro.boot.common.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jzl
 * @date 2020/3/16 11:21
 */
//@ControllerAdvice
public class MyShiroException {

    /**
     * 处理Shiro权限拦截异常
     * @return
     */
//    @ExceptionHandler(value = AuthorizationException.class)
//    @ResponseBody
    public Map<String, Object> defaultErrorHandler(){
        Map<String, Object> map = new HashMap<>();
        map.put("403", "权限不足");
        return map;
    }

    /**
     * 统一异常返回
     * @return
     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
    public Map<String, Object> errorHandler(){
        Map<String, Object> map = new HashMap<>();
        map.put("code", "666");
        map.put("msg", "统一异常返回");
        return map;
    }
}
