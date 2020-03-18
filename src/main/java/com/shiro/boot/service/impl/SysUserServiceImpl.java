package com.shiro.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.boot.entity.SysUserEntity;
import com.shiro.boot.mapper.SysUserMapper;
import com.shiro.boot.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author jzl
 * @date 2020/3/13 14:35
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {
    /**
     * 根据用户名查询实体
     * @param username
     * @return
     */
    @Override
    public SysUserEntity selectUserByName(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
