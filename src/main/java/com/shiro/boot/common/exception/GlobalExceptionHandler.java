package com.shiro.boot.common.exception;

import com.shiro.boot.common.enums.ResultCodeEnum;
import com.shiro.boot.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理器
 *
 * @author jzl
 * @date 2020/3/27 14:47
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理Shiro权限拦截异常
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Map<String, Object> defaultErrorHandler(){
        Map<String, Object> map = new HashMap<>();
        map.put("403", "权限不足");
        return map;
    }

    /**-----------------通用异常处理方法---------------**/

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.error(e.getMessage());
        return R.error();
    }

    /**-----------------参数校验异常处理方法---------------**/

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return R.error().code(ResultCodeEnum.PARAM_ERROR.getCode())
                        .message(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**-----------------自定义异常处理方法---------------**/



}
