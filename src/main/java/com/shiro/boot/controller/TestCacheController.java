package com.shiro.boot.controller;

import com.shiro.boot.entity.SysUserEntity;
import com.shiro.boot.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存测试
 * @author jzl
 * @date 2020/3/23 15:24
 */
@RestController
@RequestMapping("/cache")
public class TestCacheController {

    @Autowired
    private SysUserService sysUserService;

    @Cacheable(value = {"cache-user", "cache-user2"}, key = "'getuserby'+#userId", unless = "#result == null ")
    @RequestMapping("/user/{id}")
    public Object getUserById (@PathVariable("id") Long userId ) {
        SysUserEntity userEntity = sysUserService.getById(userId);
        return userEntity;
    }

}
