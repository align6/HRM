package com.ihrm.salarys.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.salarys.CompanySettings;
import com.ihrm.salarys.service.CompanySettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salarys")
@CrossOrigin
public class CompanySettingsController extends BaseController {

    @Autowired
    private CompanySettingsService companySettingsService;

    /**
     * 判断企业是否设置工资
     */
    @GetMapping("/company-settings")
    public Result getCompanySettings(){
        CompanySettings companySettings = companySettingsService.findById(companyId);
        return new Result(ResultCode.SUCCESS,companySettings);
    }

    /**
     * 保存企业工资设置
     */
    @PostMapping("/company-settings")
    public Result saveCompanySettings(@RequestBody CompanySettings companySettings){
        companySettings.setCompanyId(companyId);
        companySettingsService.save(companySettings);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 构造新报表
     */
    @PutMapping("/newReport/{yearMonth}")
    public Result newReport(@PathVariable String yearMonth){
        companySettingsService.newReport(companyId,yearMonth);
        return new Result(ResultCode.SUCCESS);
    }
}
