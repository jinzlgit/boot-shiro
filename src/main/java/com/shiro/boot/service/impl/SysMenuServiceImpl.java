package com.shiro.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.boot.entity.SysMenuEntity;
import com.shiro.boot.mapper.SysMenuMapper;
import com.shiro.boot.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jzl
 * @date 2020/3/16 14:08
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    @Override
    public List<SysMenuEntity> selectSysMenuByRoleId(Long roleId) {
        return this.baseMapper.selectSysMenuByRoleId(roleId);
    }
}
