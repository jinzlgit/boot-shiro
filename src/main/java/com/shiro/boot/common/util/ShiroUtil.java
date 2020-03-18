package com.shiro.boot.common.util;

import com.mysql.jdbc.Security;
import com.shiro.boot.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Objects;

/**
 * Shiro工具类
 * @author jzl
 * @date 2020/3/16 14:15
 */
public class ShiroUtil {
    /**
     * 私有构造类
     */
    public ShiroUtil() {
    }

    private static RedisSessionDAO redisSessionDAO =  SpringUtil.getBean(RedisSessionDAO.class);

    /**
     * 获取当前用户Session
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户登出
     */
    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static SysUserEntity getUserInfo(){
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 删除用户缓存信息
     * @param username
     * @param isRemoveSession
     */
    public static void deleteCache(String username, boolean isRemoveSession){
        // 从缓存中获取Session
        Session session = null;
        Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
        SysUserEntity sysUserEntity;
        Object attribute = null;
        for (Session sessionInfo : activeSessions) {
            // 遍历Session，找到该用户名称对应的Session
            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null){
                continue;
            }
            sysUserEntity = (SysUserEntity) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (sysUserEntity == null){
                continue;
            }
            if (Objects.equals(sysUserEntity.getUsername(), username)){
                session = sessionInfo;
                break;
            }
        }
        if (session == null || attribute == null){
            return;
        }
        // 删除Session
        if (isRemoveSession){
            redisSessionDAO.delete(session);
        }
        // 删除Cache，在访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authenticator = securityManager.getAuthenticator();
        ((LogoutAware) authenticator).onLogout((SimplePrincipalCollection) attribute);
    }
}
