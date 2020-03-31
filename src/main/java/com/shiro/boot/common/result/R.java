package com.shiro.boot.common.result;

import com.shiro.boot.common.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果类
 *
 * 外界只可以调用统一返回类的方法，不可以直接创建，因此构造器私有
 * 内置静态方法，返回对象
 * 为便于自定义统一结果的信息，建议使用链式编程，将返回对象实例本身，即 return this
 * 响应数据由于为json格式，可定义为 JSONObject 或 Map形式
 *
 * @author jzl
 * @date 2020/3/27 10:33
 */
@Data
public class R implements Serializable {

    private static final long serialVersionUID = -349086698786874852L;
    /**
     * 是否响应成功
     */
    private boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息描述
     */
    private String message;
    /**
     * 响应数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 构造器私有
     */
    private R() {
    }

    /**
     * 通用返回成功
     */
    public static R ok(){
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.isSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 通用返回失败，未知错误
     */
    public static R error(){
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.isSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    /**
     * 设置结果，参数为 结果类枚举
     * @param result 结果类 枚举
     */
    public static R setResult(ResultCodeEnum result){
        R r = new R();
        r.setSuccess(result.isSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /**-----------------------使用链式编程，返回类本身--------------------------**/

    /**
     * 自定义返回数据
     */
    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    /**
     * 通用设置data
     */
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    /**
     * 自定义状态信息
     */
    public R message(String message){
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     */
    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 自定义是否返回成功
     */
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }
}
