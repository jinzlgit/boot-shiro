package com.shiro.boot.controller;

import com.shiro.boot.common.util.SHA256Util;
import com.shiro.boot.common.util.ShiroUtil;
import com.shiro.boot.entity.SysUserEntity;
import com.shiro.boot.entity.SysUserRoleEntity;
import com.shiro.boot.service.SysUserRoleService;
import com.shiro.boot.service.SysUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录测试
 * @author jzl
 * @date 2020/3/17 10:46
 */
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 登录
     * @param sysUserEntity
     * @return
     */
    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody SysUserEntity sysUserEntity){
        Map<String, Object> map = new HashMap<>();
        // 进行身份验证
        try {
            // 验证身份和登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(sysUserEntity.getUsername(), sysUserEntity.getPassword());
            // 开启"记得我"功能
            //token.setRememberMe(true);
            // 进行登录操作
            // 调用 currentUser 即 subject.login(token) 方法时会调用 doGetAuthenticationInfo方法。
            subject.login(token);
        } catch (IncorrectCredentialsException e){
            map.put("code", 500);
            map.put("msg", "用户不存在或者密码错误");
            return map;
        } catch (LockedAccountException e){
            map.put("code", 500);
            map.put("msg", "用户不存在或者密码错误");
            return map;
        } catch (AuthenticationException e){
            map.put("code", 500);
            map.put("msg", "用户不存在");
            return map;
        } catch (Exception e){
            map.put("code", 500);
            map.put("msg", "未知异常");
            return map;
        }
        map.put("code", 0);
        map.put("msg", "登录成功");
        map.put("token", ShiroUtil.getSession().getId().toString());
        return map;
    }

    /**
     * 未登录
     * @return
     */
    @RequestMapping("/unauth")
    public Map<String,Object> unauth(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",500);
        map.put("msg","未登录");
        return map;
    }

    @RequestMapping("/testAddUser")
    public Map<String, Object> testAddUser(){
        // 设置基础参数
        SysUserEntity user = new SysUserEntity();
        user.setUsername("testuser");
        user.setState("NORMAL");
        // 随机生成盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        // 进行加密
        String password = "123456";
        user.setPassword(SHA256Util.sha256(password, user.getSalt()));
        // 保存用户
        sysUserService.save(user);
        // 保存角色
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setUserId(user.getUserId()); // 保存用户完后会把ID返回给用户实体
        sysUserRoleService.save(sysUserRoleEntity);
        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "添加成功");
        return map;
    }

    @RequestMapping("/testAddUser2")
    @Transactional
    public Map<String, Object> testAddUser2() {
        // 设置基础参数
        SysUserEntity user = new SysUserEntity();
        user.setUsername("testuser2");
        user.setState("NORMAL");
        // 随机生成盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        // 进行加密
        String password = "123456";
        user.setPassword(SHA256Util.sha256(password, user.getSalt()));
        // 保存用户
        sysUserService.save(user);

        // 测试事务
        int i = 0;
        int i1 = 1 / i;
        System.out.println(i1);

        // 设置基础参数
        SysUserEntity user1 = new SysUserEntity();
        user1.setUsername("testuser3");
        user1.setState("NORMAL");
        // 随机生成盐值
        String salt1 = RandomStringUtils.randomAlphanumeric(20);
        user1.setSalt(salt1);
        // 进行加密
        String password1 = "123456";
        user1.setPassword(SHA256Util.sha256(password1, user.getSalt()));
        // 保存用户
        sysUserService.save(user1);
        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "添加成功");
        return map;
    }

}
