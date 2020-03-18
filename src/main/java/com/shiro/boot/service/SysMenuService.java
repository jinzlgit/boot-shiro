package com.shiro.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.boot.entity.SysMenuEntity;

import java.util.List;

/**
 * @author jzl
 * @date 2020/3/16 14:07
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据角色ID查询权限集合
     * @param roleId
     * @return
     */
    List<SysMenuEntity> selectSysMenuByRoleId(Long roleId);
}
