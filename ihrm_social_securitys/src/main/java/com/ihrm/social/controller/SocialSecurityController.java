package com.ihrm.social.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.social_securitys.*;
import com.ihrm.social.client.SystemFeignClient;
import com.ihrm.social.service.ArchiveService;
import com.ihrm.social.service.CompanySettingsService;
import com.ihrm.social.service.PaymentItemService;
import com.ihrm.social.service.UserSocialSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/social_securitys")
@CrossOrigin
public class SocialSecurityController extends BaseController {
    @Autowired
    private CompanySettingsService companySettingsService;

    @Autowired
    private UserSocialSecurityService userSocialSecurityService;

    @Autowired
    private SystemFeignClient systemFeignClient;

    @Autowired
    private PaymentItemService paymentItemService;

    @Autowired
    private ArchiveService archiveService;

    /**
     * 查询企业员工的社保信息列表
     */
    @GetMapping("/list")
    public Result findAll(@RequestParam Integer page, @RequestParam Integer size){
        PageResult result = userSocialSecurityService.findAll(page,size,companyId);
        return new Result(ResultCode.SUCCESS,result);
    }

    /**
     * 根据用户id查询社保信息
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Map map = new HashMap();
        //根据用户id查询用户数据
        Object obj = systemFeignClient.findById(id).getData();
        map.put("user",obj);
        //查询用户id查询社保数据
        UserSocialSecurity uss = userSocialSecurityService.findById(id);
        map.put("UserSocialSecurity",uss);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 更新用户社保信息
     */
    @PutMapping("/{id}")
    public Result saveUserSocialSecurity(@RequestBody UserSocialSecurity userSocialSecurity, @PathVariable String id){
        userSocialSecurity.setUserId(id);
        userSocialSecurityService.update(userSocialSecurity);
        return new Result(ResultCode.SUCCESS);
    }
}
