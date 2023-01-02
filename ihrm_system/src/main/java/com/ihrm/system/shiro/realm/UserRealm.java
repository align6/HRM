package com.ihrm.system.shiro.realm;

import com.ihrm.common.shiro.realm.IhrmRealm;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRealm extends IhrmRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    //授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return super.doGetAuthorizationInfo(principalCollection);
    }

    //认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取用户的手机号和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String mobile = token.getUsername();
        String password = new String(token.getPassword());
        //2.根据手机号查询用户
        User user = userService.findByMobile(mobile);
        //3.判断用户是否存在，用户密码是否正确
        if (user!=null && user.getPassword().equals(password)){
            ProfileResult result = null;
            if ("user".equals(user.getLevel())){
                result = new ProfileResult(user);
            }else {
                Map map = new HashMap();
                if ("coAdmin".equals(user.getLevel())){
                    map.put("enVisible","1");
                }
                List<Permission> list = permissionService.findAll(map);
                result = new ProfileResult(user,list);
            }
            //4.构造安全数据并返回(安全数据：profileResult)
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(result,password,this.getName());
            return info;
        }
        //5.若失败，抛出异常，标识用户名或密码不匹配
        return null;
    }
}
