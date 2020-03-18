package com.shiro.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.boot.entity.SysRoleEntity;
import com.shiro.boot.mapper.SysRoleMapper;

import java.util.List;

/**
 * @author jzl
 * @date 2020/3/16 13:57
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 通过用户ID查询角色集合
     * @param userId
     * @return
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);
}
