package com.shiro.boot.controller;

import com.shiro.boot.common.util.ShiroUtil;
import com.shiro.boot.entity.SysMenuEntity;
import com.shiro.boot.entity.SysRoleEntity;
import com.shiro.boot.entity.SysRoleMenuEntity;
import com.shiro.boot.entity.SysUserEntity;
import com.shiro.boot.service.SysMenuService;
import com.shiro.boot.service.SysRoleMenuService;
import com.shiro.boot.service.SysRoleService;
import com.shiro.boot.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限测试
 * @author jzl
 * @date 2020/3/17 17:11
 */
@RestController
@RequestMapping("/menu")
public class UserMenuController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取用户信息集合
     * @return
     */
    @RequestMapping("/getUserInfoList")
    @RequiresPermissions("sys:user:info")
    public Map<String, Object> getUserInfoList(){
        Map<String, Object> map = new HashMap<>();
        List<SysUserEntity> userEntityList = sysUserService.list();
        map.put("sysUserEntityList", userEntityList);
        return map;
    }

    /**
     * 获取角色信息集合
     * @return
     */
    @RequestMapping("/getRoleInfoList")
    @RequiresPermissions("sys:role:info")
    public Map<String, Object> getRoleInfoList(){
        Map<String, Object> map = new HashMap<>();
        List<SysRoleEntity> list = sysRoleService.list();
        map.put("sysRoleEntityList", list);
        return map;
    }

    /**
     * 获取权限信息集合
     * @return
     */
    @RequestMapping("/getMenuInfoList")
    @RequiresPermissions("sys:menu:info")
    public Map<String, Object> getMenuInfoList(){
        Map<String, Object> map = new HashMap<>();
        List<SysMenuEntity> list = sysMenuService.list();
        map.put("sysMenuEntityList", list);
        return map;
    }

    /**
     * 获取所有数据
     * @Author Sans
     * @CreateTime 2019/6/19 10:38
     * @Return Map<String,Object>
     */
    @RequestMapping("/getInfoAll")
    @RequiresPermissions("sys:info:all")
    public Map<String,Object> getInfoAll(){
        Map<String,Object> map = new HashMap<>();
        List<SysUserEntity> sysUserEntityList = sysUserService.list();
        map.put("sysUserEntityList",sysUserEntityList);
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.list();
        map.put("sysRoleEntityList",sysRoleEntityList);
        List<SysMenuEntity> sysMenuEntityList = sysMenuService.list();
        map.put("sysMenuEntityList",sysMenuEntityList);
        return map;
    }

    /**
     * 添加管理员角色权限(测试动态权限更新)
     * @Author Sans
     * @CreateTime 2019/6/19 10:39
     * @Param  username 用户ID
     * @Return Map<String,Object>
     */
    @RequestMapping("/addMenu")
    public Map<String,Object> addMenu(){
        //添加管理员角色权限
        SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
        sysRoleMenuEntity.setMenuId(4L);
        sysRoleMenuEntity.setRoleId(1L);
        sysRoleMenuService.save(sysRoleMenuEntity);
        //清除缓存
        String username = "admin";
        ShiroUtil.deleteCache(username,false);
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","权限添加成功");
        return map;
    }
}
