package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonExceptionHandler;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    /**
     * 保存权限
     */
    public void save(Map<String,Object> map) throws Exception {
        String id = idWorker.nextId()+"";
        //permission对象封装在map，再转成bean
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        //根据类型构造不同的资源对象 1:菜单 2:按钮 3:api
        int type = permission.getType();
        switch (type){
            case PermissionConstants.MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setId(id);
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setId(id);
                permissionPointDao.save(point);
                break;
            case PermissionConstants.API:
                PermissionApi api = BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setId(id);
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonExceptionHandler(ResultCode.FAIL);
        }
        //保存
        permissionDao.save(permission);
    }

    /**
     * 更新权限
     */
    public void update(Map<String,Object> map) throws Exception {
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        //通过传递的权限id查询
        Permission temp = permissionDao.findById(permission.getId()).get();
        temp.setName(permission.getName());
        temp.setCode(permission.getCode());
        temp.setDescription(permission.getDescription());
        temp.setEnVisible(permission.getEnVisible());

        //根据类型构造不同的资源对象 1:菜单 2:按钮 3:api
        int type = permission.getType();
        switch (type){
            case PermissionConstants.MENU:
                PermissionMenu menu = BeanMapUtils.mapToBean(map,PermissionMenu.class);
                menu.setId(permission.getId());
                permissionMenuDao.save(menu);
                break;
            case PermissionConstants.POINT:
                PermissionPoint point = BeanMapUtils.mapToBean(map,PermissionPoint.class);
                point.setId(permission.getId());
                permissionPointDao.save(point);
                break;
            case PermissionConstants.API:
                PermissionApi api = BeanMapUtils.mapToBean(map,PermissionApi.class);
                api.setId(permission.getId());
                permissionApiDao.save(api);
                break;
            default:
                throw new CommonExceptionHandler(ResultCode.FAIL);
        }
        //保存
        permissionDao.save(temp);
    }


    /**
     * 根据id删除权限
     */
    public void deleteById(String id) throws Exception {
        Permission permission = permissionDao.findById(id).get();
        //根据类型构造不同的资源对象 1:菜单 2:按钮 3:api
        int type = permission.getType();
        switch (type){
            case PermissionConstants.MENU:
                permissionMenuDao.deleteById(id);
                break;
            case PermissionConstants.POINT:
                permissionPointDao.deleteById(id);
                break;
            case PermissionConstants.API:
                permissionApiDao.deleteById(id);
                break;
            default:
                throw new CommonExceptionHandler(ResultCode.FAIL);
        }
        permissionDao.delete(permission);
    }

    /**
     * 根据id查询权限
     */
    public Map<String, Object> findById(String id) throws Exception {
        Permission permission = permissionDao.findById(id).get();
        int type = permission.getType();
        Object object = null;
        if (type == PermissionConstants.MENU){
            object = permissionMenuDao.findById(id).get();
        }else if (type == PermissionConstants.POINT){
            object = permissionPointDao.findById(id).get();
        }else if (type == PermissionConstants.API){
            object = permissionApiDao.findById(id).get();
        }else {
            throw new CommonExceptionHandler(ResultCode.FAIL);
        }
        Map<String,Object> map = BeanMapUtils.beanToMap(object);
        Map<String,Object> perm = BeanMapUtils.beanToMap(permission);
        // 合并
        Map<String, Object> combineMap = new HashMap<>();
        combineMap.putAll(map);
        combineMap.putAll(perm);
        return combineMap;
    }

    /**
     * 查询所有权限
     * parentId：  权限之间存在父子关系
     * type:  0：菜单+按钮 1：菜单 2：按钮 3：api接口
     * envisible: 0:查询所有saas平台的权限；  1：查询企业的查询
     */
    public List<Permission> findAll(Map<String,Object> map){
        Specification<Permission> spec = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的parentId构造查询条件
                if (!StringUtils.isEmpty(map.get("parentId"))){
                    list.add(cb.equal(root.get("parentId").as(String.class),(String)map.get("parentId")));
                }
                //根据请求的enVisible构造查询条件
                if (!StringUtils.isEmpty(map.get("enVisible"))){
                    list.add(cb.equal(root.get("enVisible").as(String.class),(String)map.get("enVisible")));
                }
                //根据请求的type构造查询条件
                if (!StringUtils.isEmpty(map.get("type"))){
                    CriteriaBuilder.In<Object> in = cb.in(root.get("type"));
                    String type = (String) map.get("type");
                    if ("0".equals(type)){
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(type));
                    }
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(spec);
    }
}
