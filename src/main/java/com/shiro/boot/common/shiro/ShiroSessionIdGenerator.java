package com.shiro.boot.common.shiro;

import com.shiro.boot.common.constant.RedisConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * 自定义SessionID生成器
 * @author jzl
 * @date 2020/3/16 15:21
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
        return String.format(RedisConstant.TOKEN_PREFIX_LOGIN, sessionId);
    }

//    public static void main(String[] args) {
//        ShiroSessionIdGenerator s = new ShiroSessionIdGenerator();
//        String s1 = String.format(RedisConstant.TOKEN_PREFIX_LOGIN, "123");
//        System.out.println(s1);
//    }
}
