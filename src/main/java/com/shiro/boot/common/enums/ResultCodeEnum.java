package com.shiro.boot.common.enums;

import lombok.Getter;

/**
 * 结果类 枚举
 *
 * @author jzl
 * @date 2020/3/27 10:24
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 2000, "成功"),
    UNKNOWN_ERROR(false, 2001, "未知错误"),
    PARAM_ERROR(false, 2002, "参数错误");

    /**
     * 响应是否成功
     */
    private boolean success;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success=success;
        this.code=code;
        this.message=message;
    }

}
