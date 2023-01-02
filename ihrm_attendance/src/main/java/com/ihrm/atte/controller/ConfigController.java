package com.ihrm.atte.controller;

import com.ihrm.atte.service.ConfigService;
import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.attendance.entity.AttendanceConfig;
import com.ihrm.domain.attendance.entity.DeductionDict;
import com.ihrm.domain.attendance.entity.LeaveConfig;
import com.ihrm.domain.attendance.vo.ExtraDutyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 配置考勤设置的controller
 */
@RestController
@RequestMapping("/atte")
@CrossOrigin
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存考勤设置
     */
    @PostMapping("/config")
    public Result saveConfig(@RequestBody AttendanceConfig attendanceConfig){
        attendanceConfig.setCompanyId(companyId);
        configService.saveAtteConfig(attendanceConfig);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取考勤设置
     */
    @GetMapping("/config/item")
    public Result atteConfig(String departmentId){
        AttendanceConfig attendanceConfig = configService.getAtteConfig(companyId,departmentId);
        if (attendanceConfig == null){
            AttendanceConfig temp = new AttendanceConfig();
            temp.setId(idWorker.nextId()+"");
            temp.setCompanyId(companyId);
            temp.setDepartmentId(departmentId);
            configService.saveAtteConfig(temp);
        }
        return new Result(ResultCode.SUCCESS, attendanceConfig);
    }

    /**
     * 保存或更新请假设置
     */
    @PostMapping("/leave")
    public Result saveLeave(@RequestBody List<LeaveConfig> leaveConfigList){
        for (LeaveConfig leaveConfig : leaveConfigList){
            leaveConfig.setCompanyId(companyId);
            configService.saveLeaveConfig(leaveConfig);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取请假设置
     */
    @GetMapping("/leave/list")
    public Result leaveConfig(String departmentId){
        List<LeaveConfig> leaveConfigList = configService.getLeaveConfig(companyId,departmentId);
        return new Result(ResultCode.SUCCESS, leaveConfigList);
    }

    /**
     * 保存或更新扣款设置
     */
    @PostMapping("/deduction")
    public Result saveDeduction(@RequestBody List<DeductionDict> deductionDictList){
        for (DeductionDict deductionDict : deductionDictList){
            deductionDict.setCompanyId(companyId);
            DeductionDict temp = configService.findOneDeductionConfig(deductionDict.getCompanyId(), deductionDict.getDepartmentId(), deductionDict.getDedTypeCode());
            configService.saveDeductionConfig(deductionDict);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取扣款设置
     */
    @GetMapping("/deduction/list")
    public Result deductionConfig(String departmentId){
        List<DeductionDict> deductionDictList = configService.getDeductionConfig(companyId,departmentId);
        return new Result(ResultCode.SUCCESS, deductionDictList);
    }

    /**
     * 保存或更新加班设置
     */
    @PostMapping("/extraDuty")
    public Result saveExtraDuty(@RequestBody @Valid ExtraDutyVo extraDutyVo){
        extraDutyVo.setCompanyId(companyId);
        configService.saveExtraDutyConfig(extraDutyVo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取加班设置
     */
    @GetMapping("/extraDuty/item")
    public Result extraDutyConfig(String departmentId){
        Map map = configService.getExtraDutyConfig(companyId,departmentId);
        return new Result(ResultCode.SUCCESS, map);
    }
}
