package com.shiro.boot.controller;

import com.shiro.boot.common.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色测试
 * @author jzl
 * @date 2020/3/17 13:12
 */
@RestController
@RequestMapping("/role")
public class UserRoleController {

    /**
     * 管理员角色测试接口
     * @return
     */
    @RequestMapping("/getAdminInfo")
    @RequiresRoles("ADMIN")
    public Map<String,Object> getAdminInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","这里是只有管理员角色能访问的接口");
        return map;
    }

    /**
     * 用户角色测试接口
     * @return
     */
    @RequestMapping("/getUserInfo")
    @RequiresRoles("USER")
    public Map<String,Object> getUserInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","这里是只有用户角色能访问的接口");
        return map;
    }

    /**
     * 角色测试接口
     * @return
     */
    @RequestMapping("/getRoleInfo")
    @RequiresRoles(value = {"ADMIN", "USER"}, logical = Logical.OR)
    @RequiresUser
    public Map<String, Object> getRoleInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","这里是只要有ADMIN或者USER角色能访问的接口");
        return map;
    }

    @RequestMapping("/getLogout")
    @RequiresUser
    public Map<String, Object> getLogout(){
        // 登出操作：Shiro会帮我们清理掉Session和Cache
        ShiroUtil.logout();
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","登出");
        return map;
    }

}
