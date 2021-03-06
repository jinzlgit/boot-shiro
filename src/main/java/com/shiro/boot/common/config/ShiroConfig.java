package com.shiro.boot.common.config;

import com.shiro.boot.common.constant.RedisConstant;
import com.shiro.boot.common.shiro.ShiroRealm;
import com.shiro.boot.common.shiro.ShiroSessionIdGenerator;
import com.shiro.boot.common.shiro.ShiroSessionManager;
import com.shiro.boot.common.util.SHA256Util;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * @author jzl
 * @date 2020/3/16 17:43
 */
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    private final int EXPIRE = 1800; // Session过期时间 30分钟

    // Redis配置
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 开启Shiro-aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro基础配置
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置，Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 这里的index是后台接口名，非页面，登录成功后要跳转的连接
        // 配置Shiro默认登录界面地址，前后端分离项目中登录界面跳转应由前端路由控制，后台仅返回json数据
        //shiroFilterFactoryBean.setSuccessUrl("/index");

//        // 自定义拦截器限制并发人数
//        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
//        // 限制同一账号同时在线的个数
//        filtersMap.put("kickout", kickoutSessionControlFilter());
//        // 统计登录人数
//        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 注意过滤器配置顺序不能颠倒
        // 配置过滤：不会被拦截的连接
        filterChainDefinitionMap.put("/static/**", "anon");

        filterChainDefinitionMap.put("/swagger-ui.html", "anon"); // 放行 API 文档接口
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/api-docs/**","anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**","anon");

        filterChainDefinitionMap.put("/userLogin/**", "anon");
        filterChainDefinitionMap.put("/test/**", "anon"); // 放行测试类
        filterChainDefinitionMap.put("/thread/**", "anon"); // 放行测试类
        filterChainDefinitionMap.put("/mq/**", "anon"); // 放行测试类
        filterChainDefinitionMap.put("/cache/**", "anon"); // 放行缓存测试类的权限
        // 如果开启限制同一账号登录，改为 "kickout,authc"
        filterChainDefinitionMap.put("/**", "authc");

        // 这里是后台的接口名，非页面，如果不设置会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/userLogin/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义Session管理
        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现
        securityManager.setCacheManager(redisCacheManager());
        // 自定义Realm验证
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    /**
     * 凭证匹配器
     * 将密码校验交给Shiro的SimpleAuthorizationInfo进行处理，在这里做匹配设置
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法：这里使用SHA-256算法
        hashedCredentialsMatcher.setHashAlgorithmName(SHA256Util.HASH_ALGORITHM_NAME);
        // 散列次数，比如散列两次，相当于 md5(md5(""))
        hashedCredentialsMatcher.setHashIterations(SHA256Util.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }

    /**
     * 身份验证器
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 配置Redis管理器：使用的是Shiro-Redis开源插件
     * @return
     */
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * 配置Cache管理器：用于往Redis存储权限和角色标识
     * 使用的是Shiro-redis开源插件
     *
     * 此处的实例名(原为cacheManager) 会与SpringBoot自动配置redis缓存中的实例冲突，可将此处改名
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        cacheManager.setKeyPrefix(CACHE_KEY);
        // 配置缓存的话要求放在session里面的实体类必须有个id标识
        cacheManager.setPrincipalIdFieldName("userId");
        return cacheManager;
    }

    /**
     * SessionID生成器
     * @return
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator(){
        return new ShiroSessionIdGenerator();
    }

    /**
     * 配置RedisSessionDAO：使用的是Shiro-redis开源插件
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setExpire(EXPIRE);
        return redisSessionDAO;
    }

    /**
     * 配置Session管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
}
