package com.ihrm.atte.controller;

import com.ihrm.atte.service.AttendanceService;
import com.ihrm.atte.service.ExcelImportService;
import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.attendance.entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/atte")
public class AttendanceController extends BaseController {

    @Autowired
    private ExcelImportService excelImportService;

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 上传考勤数据
     */
    @PostMapping("/import")
    public Result importExcel(@RequestParam(name = "file")MultipartFile file) throws Exception {
        excelImportService.importAttendanceExcel(file,companyId);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询考勤数据列表
     */
    @GetMapping("")
    public Result getAtteData(int page,int size) throws ParseException {
        Map map = attendanceService.getAtteData(companyId,page,size);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 更新用户考勤记录
     */
    @PutMapping("/{id}")
    public Result updateAtteData(@RequestBody Attendance attendance){
        attendanceService.updateAtteData(attendance, companyId);
        return new Result(ResultCode.SUCCESS);
    }
}
