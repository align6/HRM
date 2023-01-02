package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Permission;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/system")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 保存权限
     * 1：菜单 2：按钮 3：api接口
     * 不同类型对应不同的实体类型
     */
    @PostMapping("/permission")
    public Result save(@RequestBody Map<String,Object> map) throws Exception {
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改权限
     */
    @PutMapping("/permission/{id}")
    public Result update(@PathVariable String id, @RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除权限
     */
    @DeleteMapping("/permission/{id}")
    public Result deleteById(@PathVariable String id) throws Exception {
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询权限
     */
    @GetMapping("/permission/{id}")
    public Result findById(@PathVariable String id) throws Exception {
        Map<String,Object> permission = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS, permission);
    }

    /**
     * 查询权限所有列表
     * parentId：  权限之间存在父子关系
     * type:  0：菜单+按钮 1：菜单 2：按钮 3：api接口
     * envisible: 0:查询所有saas平台的权限；  1：查询企业的查询
     */
    @GetMapping("/permission")
    public Result findAll(@RequestParam Map map){
        List<Permission> list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS, list);
    }
}
