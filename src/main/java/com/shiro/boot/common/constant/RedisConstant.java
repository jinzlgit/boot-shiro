package com.shiro.boot.common.constant;

/**
 * Redis常量类
 * @author jzl
 * @date 2020/3/16 15:24
 */
public interface RedisConstant {

    /**
     *TOKEN前缀
     */
    String TOKEN_PREFIX_LOGIN = "login_token_%s";

    /**
     * 过期两小时
     */
    Integer REDIS_EXPIRE_TWO = 7200;

    /**
     * 过期15分
     */
    Integer REDIS_EXPIRE_EMAILL = 900;

    /**
     * 过期5分钟
     */
    Integer REDIS_EXPIRE_KAPCHA = 300;

    /**
     * 无过期时间
     */
    Integer REDIS_EXPIRE_NULL = -1;
}
