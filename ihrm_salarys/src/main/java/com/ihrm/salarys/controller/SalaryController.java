package com.ihrm.salarys.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.salarys.UserSalary;
import com.ihrm.domain.salarys.vo.UserSalaryVo;
import com.ihrm.salarys.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/salarys")
@CrossOrigin
public class SalaryController extends BaseController {

    @Autowired
    private SalaryService salaryService;

    /**
     * 保存工资
     */
    @PostMapping("/userSalary/{userId}")
    public Result saveSalary(@RequestBody UserSalary userSalary){
        userSalary.setFixedBasicSalary(userSalary.getCurrentBasicSalary());
        userSalary.setFixedPostWage(userSalary.getCurrentPostWage());
        salaryService.saveUserSalary(userSalary);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 更新工资
     */
    @PutMapping("/userSalary/{userId}")
    public Result changeSalary(@RequestBody UserSalary userSalary){
        salaryService.saveUserSalary(userSalary);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询用户工资
     */
    @GetMapping("/userSalary/{userId}")
    public Result getSalary(@PathVariable String userId){
        UserSalary userSalary = salaryService.findUserSalary(userId);
        return new Result(ResultCode.SUCCESS,userSalary);
    }

    /**
     * 分页查询所有用户工资
     */
    @GetMapping("/userSalary/list")
    public Result findAllSalary(@RequestParam Integer page, @RequestParam Integer size){
        PageResult result = salaryService.findAll(page,size,companyId);
        return new Result(ResultCode.SUCCESS,result);
    }

    /**
     * 获取用户工资明细
     */
    @GetMapping("/{userId}")
    public Result getDetail(@PathVariable String userId, String yearMonth){
        UserSalaryVo userSalaryVo = salaryService.getDetail(userId,yearMonth,companyId);
        return new Result(ResultCode.SUCCESS,userSalaryVo);
    }
}
