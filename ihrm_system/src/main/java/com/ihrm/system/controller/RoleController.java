package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.response.RoleResult;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/system")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     *新增角色
     */
    @PostMapping("/role")
    public Result save(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 更新角色
     */
    @PutMapping("/role/{id}")
    public Result update(@PathVariable String id, @RequestBody Role role){
        role.setId(id);
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除角色
     */
    @DeleteMapping("/role/{id}")
    public Result delete(@PathVariable String id){
        roleService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询角色
     */
    @GetMapping("/role/{id}")
    public Result findById(@PathVariable String id){
        Role role = roleService.findById(id);
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS, roleResult);
    }

    /**
     * 分页查询角色
     */
    @GetMapping("/role")
    public Result findByPage(int page, int size){
        Page<Role> rolePage = roleService.findByPage(companyId, page, size);
        PageResult pageResult = new PageResult(rolePage.getTotalElements(),rolePage.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 查询所有角色
     */
    @GetMapping("/role/list")
    public Result findAll(){
        List<Role> roleList = roleService.findAll(companyId);
        return new Result(ResultCode.SUCCESS,roleList);
    }

    /**
     * 分配权限
     */
    @PutMapping("/role/assignPerms")
    public Result assignPerms(@RequestBody Map<String,Object> map){
        //获取被分配的角色id
        String roleId = (String) map.get("id");
        //获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //调用service完成权限分配
        roleService.assignPerms(roleId,permIds);
        return new Result(ResultCode.SUCCESS);
    }
}
