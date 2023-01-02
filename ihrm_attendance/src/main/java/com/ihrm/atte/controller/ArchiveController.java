package com.ihrm.atte.controller;

import com.ihrm.atte.service.ArchiveService;
import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.attendance.entity.ArchiveMonthly;
import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atte")
public class ArchiveController extends BaseController {

    @Autowired
    private ArchiveService archiveService;

    /**
     * 获取当月报表的考勤归档数据
     */
    @GetMapping("/archive")
    public Result atteArchive(@RequestParam String atteDate){
        List<ArchiveMonthlyInfo> list = archiveService.getReports(companyId,atteDate);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 进行考勤数据归档
     */
    @GetMapping("/archive/item")
    public Result archiveData(String archiveDate){
        archiveService.saveArchive(archiveDate,companyId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 判断是否已经归档
     */
    @GetMapping("/isArchive/{yearsMonth}")
    public Result isArchive(@PathVariable String yearsMonth){
        ArchiveMonthly archiveMonthly = archiveService.findArchive(companyId,yearsMonth);
        if (archiveMonthly == null){
            return new Result(ResultCode.SUCCESS,"未归档");
        }
        return new Result(ResultCode.SUCCESS,"已归档");
    }

    /**
     * 新建报表
     */
    @GetMapping("/newReports")
    public Result newReports(String atteDate){
        archiveService.newReports(atteDate,companyId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查看历史归档
     */
    @GetMapping("/reports/year")
    public Result historyReports(String year){
        List<ArchiveMonthly> list = archiveService.findHistoryReports(year,companyId);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 查看历史归档明细
     */
    @GetMapping("/reports/{id}")
    public Result historyReportDetail(@PathVariable String id){
        List<ArchiveMonthlyInfo> list = archiveService.findHistoryReportDetail(id);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 查看某个用户的历史归档明细
     */
    @GetMapping("/archive/{userId}/{yearMonth}")
    public Result historyData(@PathVariable String userId,@PathVariable String yearMonth){
        ArchiveMonthlyInfo info = archiveService.findUserHistoryData(userId,yearMonth);
        return new Result(ResultCode.SUCCESS,info);
    }
}
