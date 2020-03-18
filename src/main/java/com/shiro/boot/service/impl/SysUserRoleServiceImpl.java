package com.shiro.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.boot.entity.SysUserRoleEntity;
import com.shiro.boot.mapper.SysUserRoleMapper;
import com.shiro.boot.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author jzl
 * @date 2020/3/16 14:11
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {
}
