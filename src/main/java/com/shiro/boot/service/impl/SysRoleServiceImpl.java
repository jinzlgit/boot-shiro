package com.shiro.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.boot.entity.SysRoleEntity;
import com.shiro.boot.mapper.SysRoleMapper;
import com.shiro.boot.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jzl
 * @date 2020/3/16 14:01
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {
    @Override
    public List<SysRoleEntity> selectSysRoleByUserId(Long userId) {
        return this.baseMapper.selectSysRoleByUserId(userId);
    }
}
