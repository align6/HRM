package com.ihrm.social.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.social_securitys.Archive;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import com.ihrm.domain.social_securitys.CompanySettings;
import com.ihrm.social.service.ArchiveService;
import com.ihrm.social.service.CompanySettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/social_securitys")
@CrossOrigin
public class ArchiveController extends BaseController {
    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private CompanySettingsService companySettingsService;

    /**
     * 查询月份数据报表
     * @param yearsMonth 月份
     * @param opType 1（未归档的数据） 其他（历史归档的数据）
     * @return
     */
    @GetMapping("/historys/{yearsMonth}")
    public Result historyDetail(@PathVariable String yearsMonth, int opType){
        List<ArchiveDetail> archiveList = new ArrayList<>();
        if (opType == 1){
            //未归档，查询当月数据
            archiveList = archiveService.getReports(companyId,yearsMonth);
        }else {
            //根据月和企业id查询归档历史，已存在则返回数据
            Archive archive = archiveService.findArchive(companyId,yearsMonth);
            if (archive != null){
                archiveList = archiveService.findAllDetailsByArchiveId(archive.getId());
            }
        }
        return new Result(ResultCode.SUCCESS,archiveList);
    }

    /**
     * 将数据进行归档，即存入archive中
     */
    @PostMapping("/historys/{yearsMonth}/archive")
    public Result historyArchive(@PathVariable String yearsMonth){
        archiveService.archive(yearsMonth,companyId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 制作新报表，切换统计周期
     */
    @PutMapping("/historys/{yearsMonth}/newReport")
    public Result newReport(@PathVariable String yearsMonth){
        CompanySettings companySettings = companySettingsService.findById(companyId);
        if (companySettings == null){
            companySettings = new CompanySettings();
        }
        companySettings.setCompanyId(companyId);
        companySettings.setDataMonth(yearsMonth);
        companySettingsService.save(companySettings);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 判断是否已经归档
     */
    @GetMapping("/historys/{yearsMonth}/isArchive")
    public Result isArchive(@PathVariable String yearsMonth){
        Archive archive = archiveService.findArchive(companyId,yearsMonth);
        if (archive == null){
            return new Result(ResultCode.SUCCESS,"未归档");
        }
        return new Result(ResultCode.SUCCESS,"已归档");
    }

    /**
     * 查询历史归档列表
     */
    @GetMapping("/historys/{year}/list")
    public Result historyList(@PathVariable String year){
        List<Archive> list = archiveService.findArchiveByYear(companyId,year);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 根据用户id和社保年月查询用户社保归档明细
     */
    @GetMapping("/historys/archiveDetail/{userId}/{yearMonth}")
    public Result historyData(@PathVariable String userId,@PathVariable String yearMonth){
        ArchiveDetail archiveDetail = archiveService.findUserArchiveDetail(userId,yearMonth);
        return new Result(ResultCode.SUCCESS,archiveDetail);
    }
}
