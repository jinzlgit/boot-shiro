package com.shiro.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.boot.entity.SysMenuEntity;

import java.util.List;

/**
 * @author jzl
 * @date 2020/3/13 14:20
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 根据角色查询角色权限
     * @param roleId
     * @return
     */
    List<SysMenuEntity> selectSysMenuByRoleId(Long roleId);

}
