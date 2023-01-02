package com.ihrm.salarys.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.salarys.SalaryArchive;
import com.ihrm.domain.salarys.SalaryArchiveDetail;
import com.ihrm.salarys.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/salarys")
@CrossOrigin
public class ArchiveController extends BaseController {

    @Autowired
    private ArchiveService archiveService;

    /**
     * 制作工资月报表
     *  opType=1:新制作的报表
     *        =其他：查询已归档的报表
     */
    @GetMapping("/reports/{yearMonth}")
    public Result historyDetail(@PathVariable String yearMonth,int opType){
        List<SalaryArchiveDetail> list = new ArrayList<>();
        if (opType == 1){
            list = archiveService.getReports(yearMonth,companyId);
        }else {
            SalaryArchive salaryArchive = archiveService.findSalaryArchive(yearMonth,companyId);
            if (salaryArchive != null){
                list = archiveService.findSalaryArchiveDetail(salaryArchive.getId());
            }
        }
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 查询历史归档列表
     */
    @GetMapping("/reports/{year}/list")
    public Result historyList(@PathVariable String year){
        List<SalaryArchive> list = archiveService.findArchiveByYear(companyId,year);
        return new Result(ResultCode.SUCCESS,list);
    }
}
