package com.shiro.boot.common.shiro;

import com.shiro.boot.common.util.ShiroUtil;
import com.shiro.boot.entity.SysMenuEntity;
import com.shiro.boot.entity.SysRoleEntity;
import com.shiro.boot.entity.SysUserEntity;
import com.shiro.boot.service.SysMenuService;
import com.shiro.boot.service.SysRoleService;
import com.shiro.boot.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shiro权限匹配和账户密码匹配
 * @author jzl
 * @date 2020/3/16 16:25
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 授权权限
     * 用户进行权限验证时Shiro会去缓存中找，如果查不到数据，会执行这个方法去查权限，并放入缓存中
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户ID
        SysUserEntity sysUserEntity = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        Long userId = sysUserEntity.getUserId();
        // 这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        // 查询角色和权限（这里根据业务自行查询）
        List<SysRoleEntity> sysRoleEntyList = sysRoleService.selectSysRoleByUserId(userId);
        for (SysRoleEntity sysRoleEntity : sysRoleEntyList) {
            rolesSet.add(sysRoleEntity.getRoleName());
            List<SysMenuEntity> sysMenuEntityList = sysMenuService.selectSysMenuByRoleId(sysRoleEntity.getRoleId());
            for (SysMenuEntity sysMenuEntity : sysMenuEntityList) {
                permsSet.add(sysMenuEntity.getPerms());
            }
        }
        // 将查询到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setRoles(rolesSet);
        authorizationInfo.setStringPermissions(permsSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户输入的账号
        String username = (String) authenticationToken.getPrincipal();
        // 通过username从数据库中查找 User 对象，如果找到进行验证
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔限制，2分钟内不会重复执行该方法
        SysUserEntity user = sysUserService.selectUserByName(username);
        // 判断账号是否存在
        if (user == null){
            throw new AuthenticationException();
        }
        // 判断账号是否被冻结
        if (user.getState() == null || "PROHIBIT".equals(user.getState())){
            throw new LockedAccountException();
        }
        // 进行验证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,                                       // 用户信息
                user.getPassword(),                         // 密码
                ByteSource.Util.bytes(user.getSalt()),      // 设置盐值
                getName()                                   // 返回一个唯一的Realm名字
        );
        // 验证成功开始踢人（清楚缓存和Sess）
        ShiroUtil.deleteCache(username, true);
        return authenticationInfo;
    }
}
