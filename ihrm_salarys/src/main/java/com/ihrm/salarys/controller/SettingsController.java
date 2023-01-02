package com.ihrm.salarys.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.salarys.Settings;
import com.ihrm.salarys.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salarys")
@CrossOrigin
public class SettingsController extends BaseController {

    @Autowired
    private SettingsService settingsService;

    /**
     * 保存企业计薪和津贴设置
     */
    @PostMapping("/settings")
    public Result saveSettings(@RequestBody Settings settings){
        settings.setCompanyId(companyId);
        settingsService.save(settings);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取企业计薪和津贴设置
     */
    @GetMapping("/settings")
    public Result getSettings(){
        Settings settings = settingsService.findById(companyId);
        return new Result(ResultCode.SUCCESS,settings);
    }
}
