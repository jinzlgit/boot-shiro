package com.shiro.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.boot.entity.SysUserEntity;

/**
 * @author jzl
 * @date 2020/3/13 14:32
 */
public interface SysUserService extends IService<SysUserEntity> {

    SysUserEntity selectUserByName(String username);
}
