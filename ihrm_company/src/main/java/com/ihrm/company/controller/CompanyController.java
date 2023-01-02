package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域问题
@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * 新增企业
     */
    @PostMapping("")
    public Result save(@RequestBody Company company){
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id更新企业
     */
    @PutMapping( "/{id}")
    public Result update(@PathVariable String id, @RequestBody Company company){
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除企业
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询企业
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Company company = companyService.findById(id);
        return new Result(ResultCode.SUCCESS,company);
    }

    /**
     * 查询所有企业
     */
    @GetMapping("")
    public Result findAll(){
        List<Company> list = companyService.findAll();
        return new Result(ResultCode.SUCCESS, list);
    }

}

