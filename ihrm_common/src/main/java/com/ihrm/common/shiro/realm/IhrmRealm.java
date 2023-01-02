package com.ihrm.common.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.ihrm.domain.system.response.ProfileResult;

import java.util.Set;

//公共的realm：获取安全数据，构造权限信息
public class IhrmRealm extends AuthorizingRealm {

    public void setName(String name){
       super.setName("ihrmRealm");
    }

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取安全数据
        ProfileResult result = (ProfileResult) principalCollection.getPrimaryPrincipal();
        //2.获取权限信息
        Set<String> apis = (Set<String>) result.getRoles().get("apis");
        //3.构造权限数据
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(apis);
        return info;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
