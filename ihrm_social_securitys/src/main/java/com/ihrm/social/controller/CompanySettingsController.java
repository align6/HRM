package com.ihrm.social.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.social_securitys.CompanySettings;
import com.ihrm.social.service.CompanySettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social_securitys")
@CrossOrigin
public class CompanySettingsController extends BaseController {
    @Autowired
    private CompanySettingsService companySettingsService;

    /**
     * 保存企业设置
     */
    @PostMapping("/settings")
    public Result saveSettings(@RequestBody CompanySettings companySettings){
        companySettings.setCompanyId(companyId);
        companySettingsService.save(companySettings);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询企业是否设置过社保
     */
    @GetMapping("/settings")
    public Result settings(){
        CompanySettings companySettings = companySettingsService.findById(companyId);
        return new Result(ResultCode.SUCCESS,companySettings);
    }
}
