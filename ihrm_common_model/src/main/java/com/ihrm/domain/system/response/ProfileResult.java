package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
public class ProfileResult implements Serializable, AuthCachePrincipal {

    private static final long serialVersionUID = 5311639668748214232L;

    private String mobile; //手机号码

    private String username;  //用户名称

    private String company;  //公司名称

    private String companyId;  //公司id

    private String userId; //用户id

    private Map<String,Object> roles = new HashMap<>();  //用户拥有的权限

    /**
     * 根据token获取个人信息
     */
    public ProfileResult(User user){
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();
        this.companyId = user.getCompanyId();
        this.userId = user.getId();
        Set<Role> roles = user.getRoles();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Role role : roles){
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions){
                String code = permission.getCode();
                /**
                 * 不可以使用PermissionConstants，因为已经在common的pom文件引用model，
                 * 使用PermissionConstants需要在model的pom文件中引入common，导致死循环
                 */
                if (permission.getType() == 1){
                    menus.add(code);
                }else if (permission.getType() == 2){
                    points.add(code);
                }else {
                    apis.add(code);
                }
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }

    public ProfileResult(User user, List<Permission> list){
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.company = user.getCompanyName();
        this.companyId = user.getCompanyId();
        this.userId = user.getId();
        Set<String> menus = new HashSet<>();
        Set<String> points = new HashSet<>();
        Set<String> apis = new HashSet<>();
        for (Permission permission : list){
            String code = permission.getCode();
            if (permission.getType() == 1){
                menus.add(code);
            }else if (permission.getType() == 2){
                points.add(code);
            }else {
                apis.add(code);
            }
        }
        this.roles.put("menus",menus);
        this.roles.put("points",points);
        this.roles.put("apis",apis);
    }

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
