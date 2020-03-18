package com.shiro.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.boot.entity.SysRoleEntity;

import java.util.List;

/**
 * @author jzl
 * @date 2020/3/13 14:24
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 通过用户ID查询角色集合
     * @param userId
     * @return
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);
}
