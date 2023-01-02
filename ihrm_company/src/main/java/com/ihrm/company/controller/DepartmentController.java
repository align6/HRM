package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.client.UserFeignClient;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     *新增部门
     */
    @PostMapping("/department")
    public Result save(@RequestBody Department department){
        department.setCompanyId(companyId);
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 更新部门
     */
    @PutMapping("/department/{id}")
    public Result update(@PathVariable String id, @RequestBody Department department){
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id进行删除
     */
    @DeleteMapping("/department/{id}")
    public Result delete(@PathVariable String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询部门
     */
    @GetMapping("/department/{id}")
    public Result findById(@PathVariable String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS, department);
    }

    /**
     * 查询所有列表
     */
    @GetMapping("/department")
    public Result findAll(){
        Company company = companyService.findById(companyId);
        List<Department> list = departmentService.findAll(company.getId());
        DeptListResult deptListResult = new DeptListResult(company,list);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

    /**
     * 根据部门编码和企业id查询部门
     *      跨微服务，用于/system/import
     *      用于上传文件时将文件中的部门编码转换成部门id,再将部门id传入user表中
     */
    @PostMapping("/department/search")
    public Department findByCode(@RequestParam String code){
        return departmentService.findByCode(code,companyId);
    }

}
