package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoleResult implements Serializable {
    private static final long serialVersionUID = -8161096581020009158L;

    @Id
    private String id;  //主键

    private String name;  //角色名

    private String description;  //说明

    private String companyId;  //企业id

    private List<String> permIds = new ArrayList<>();

    public RoleResult(Role role){
        BeanUtils.copyProperties(role,this);
        for (Permission permission : role.getPermissions()){
            this.permIds.add(permission.getId());
        }
    }
}
