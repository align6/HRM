package com.ihrm.system.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService extends BaseService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 添加角色
     */
    public void save(Role role){
        String id = idWorker.nextId()+"";
        role.setId(id);
        roleDao.save(role);
    }

    /**
     * 更新角色
     */
    public void update(Role role){
        Role temp = roleDao.findById(role.getId()).get();
        temp.setDescription(role.getDescription());
        temp.setName(role.getName());
        roleDao.save(temp);
    }

    /**
     * 根据id删除角色
     */
    public void deleteById(String id){
        roleDao.deleteById(id);
    }

    /**
     * 根据id查询角色
     */
    public Role findById(String id){
        return roleDao.findById(id).get();
    }

    /**
     * 分页查询角色
     */
    public Page findByPage(String companyId, int page, int size){
        return roleDao.findAll(getSpec(companyId), PageRequest.of(page-1, size));
    }

    /**
     * 查询所有角色
     */
    public List<Role> findAll(String companyId){
        return roleDao.findAll(getSpec(companyId));
    }

    /**
     * 分配权限
     */
    public void assignPerms(String roleId, List<String> permIds) {
        //根据id查询角色
        Role role = roleDao.findById(roleId).get();
        //构造用户的权限集合
        Set<Permission> permissions = new HashSet<>();
        for (String permId : permIds){
            Permission permission = permissionDao.findById(permId).get();
            //若为父id,分配其子权限
            List<Permission> apiList = permissionDao.findByTypeAndParentId(PermissionConstants.API,permission.getId());
            permissions.addAll(apiList);
            permissions.add(permission);
        }
        //设置角色和权限的关系
        role.setPermissions(permissions);
        //更新角色
        roleDao.save(role);
    }
}
