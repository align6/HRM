package com.ihrm.employee.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.poi.ExcelExportUtil;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.DownloadUtils;
import com.ihrm.domain.employee.*;
import com.ihrm.domain.employee.response.EmployeeReportResult;
import com.ihrm.employee.service.*;
import net.sf.jasperreports.engine.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController extends BaseController {

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private PositiveService positiveService;

    @Autowired
    private ResignationService resignationService;

    @Autowired
    private TransferPositionService transferPositionService;

    @Autowired
    private UserCompanyJobsService userCompanyJobsService;

    @Autowired
    private UserCompanyPersonalService userCompanyPersonalService;


    /**
     * ??????????????????
     */
    @GetMapping("/archives")
    public Result findArchives(@RequestParam String year, @RequestParam Integer page, @RequestParam Integer size) {
        Map map = new HashMap();
        map.put("year", year);
        map.put("companyId", companyId);
        Page<EmployeeArchive> searchPage = archiveService.findSearch(map, page, size);
        PageResult<EmployeeArchive> result = new PageResult(searchPage.getTotalElements(), searchPage.getContent());
        return new Result(ResultCode.SUCCESS, result);
    }

    /**
     * ?????????????????????
     */
    @PostMapping("/{id}/positive")
    public Result savePositive(@PathVariable String userId, @RequestBody EmployeePositive positive) {
        positive.setUserId(userId);
        positiveService.save(positive);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * ?????????????????????
     * ??????????????????????????????????????????????????????
     */
    @GetMapping("/{id}/positive")
    public Result findPositive(@PathVariable String id) {
        EmployeePositive positive = positiveService.findById(id);
        if (positive == null) {
            positive = new EmployeePositive();
            positive.setUserId(id);
            positive.setStatus(1);
            positive.setCreateTime(new Date());
        }
        return new Result(ResultCode.SUCCESS, positive);
    }

    /**
     * ?????????????????????
     */
    @PostMapping("/{id}/resignation")
    public Result saveResignation(@PathVariable String id, @RequestBody EmployeeResignation resignation) {
        resignation.setUserId(id);
        resignationService.save(resignation);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * ?????????????????????
     * ??????????????????????????????????????????????????????
     */
    @GetMapping("/{id}/resignation")
    public Result findResignation(@PathVariable String id) {
        EmployeeResignation resignation = resignationService.findById(id);
        if (resignation == null) {
            resignation = new EmployeeResignation();
            resignation.setUserId(id);
        }
        return new Result(ResultCode.SUCCESS, resignation);
    }

    /**
     * ?????????????????????
     */
    @PostMapping("/{id}/transferPosition")
    public Result saveTransferPosition(@PathVariable String id, @RequestBody EmployeeTransferPosition transferPosition) {
        transferPosition.setUserId(id);
        transferPositionService.save(transferPosition);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * ?????????????????????
     * ??????????????????????????????????????????????????????
     */
    @GetMapping("/{id}/transferPosition")
    public Result findTransferPosition(@PathVariable String id) {
        EmployeeTransferPosition transferPosition = transferPositionService.findById(id);
        if (transferPosition == null) {
            transferPosition = new EmployeeTransferPosition();
            transferPosition.setUserId(id);
        }
        return new Result(ResultCode.SUCCESS, transferPosition);
    }

    /**
     * ???????????????????????????
     */
    @PostMapping("/{id}/jobs")
    public Result saveJobInfo(@PathVariable String id, @RequestBody UserCompanyJobs userCompanyJobs) {
        if (userCompanyJobs == null) {
            userCompanyJobs = new UserCompanyJobs();
            userCompanyJobs.setUserId(id);
            userCompanyJobs.setCompanyId(companyId);
        }
        userCompanyJobsService.save(userCompanyJobs);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * ???????????????????????????
     * ????????????????????????????????????????????????????????????
     */
    @GetMapping("/{id}/jobs")
    public Result findJobInfo(@PathVariable String id) {
        UserCompanyJobs jobsInfo = userCompanyJobsService.findById(id);
        if (jobsInfo == null) {
            jobsInfo = new UserCompanyJobs();
            jobsInfo.setUserId(id);
            jobsInfo.setCompanyId(companyId);
        }
        return new Result(ResultCode.SUCCESS, jobsInfo);
    }

    /**
     * ???????????????????????????
     */
    @PostMapping("/{id}/personal")
    public Result savePersonInfo(@PathVariable String id, @RequestBody Map map) throws Exception {
        UserCompanyPersonal personalInfo = BeanMapUtils.mapToBean(map, UserCompanyPersonal.class);
        if (personalInfo == null) {
            personalInfo = new UserCompanyPersonal();
            personalInfo.setUserId(id);
            personalInfo.setCompanyId(companyId);
        }
        userCompanyPersonalService.save(personalInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * ???????????????????????????
     * ????????????????????????????????????????????????????????????
     */
    @GetMapping("/{id}/personal")
    public Result findPersonInfo(@PathVariable String id) {
        UserCompanyPersonal personalInfo = userCompanyPersonalService.findById(id);
        if (personalInfo == null) {
            personalInfo = new UserCompanyPersonal();
            personalInfo.setUserId(id);
            personalInfo.setCompanyId(companyId);
        }
        return new Result(ResultCode.SUCCESS, personalInfo);
    }

    @GetMapping("/archives/{month}")
    public Result refort(@PathVariable String month){
        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);
        System.out.println(list);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * ????????????????????????
     * ????????????-??? -- entryTime??????resignationTime????????????
     *      SXSSFWorkbook  ??????????????????  ?????????????????????
     */
    @GetMapping("/export/{month}")
    public void export(@PathVariable String month) throws IOException {
        //1.??????????????????
        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);
        System.out.println(list);
        //2.??????excel
//        Workbook wb = new XSSFWorkbook();  
        SXSSFWorkbook wb = new SXSSFWorkbook(100);  //?????????????????????????????????????????????
        Sheet sheet = wb.createSheet();
        /**
         * ?????????????????????
         */
        String[] titles = "??????id,????????????,????????????,????????????,????????????,????????????,????????????,????????????,????????????".split(",");
        Row row = sheet.createRow(0);
        int titleIndex = 0; //???????????????
        for (String title : titles) {
            Cell cell = row.createCell(titleIndex++);
            cell.setCellValue(title);
        }
        /**
         * ??????????????????????????????????????????
         */
        int rowIndex = 1;
        Cell cell = null;
        for (EmployeeReportResult result : list) {
            row = sheet.createRow(rowIndex++);
            //??????id
            cell = row.createCell(0);
            sheet.setColumnWidth(0, 20 * 256);
            cell.setCellValue(result.getUserId());
            //????????????
            cell = row.createCell(1);
            cell.setCellValue(result.getUsername());
            //????????????
            cell = row.createCell(2);
            sheet.setColumnWidth(2, 15 * 256);
            cell.setCellValue(result.getMobile());
            //????????????
            cell = row.createCell(3);
            cell.setCellValue(result.getNationalArea());
            //????????????
            cell = row.createCell(4);
            cell.setCellValue(result.getDepartmentName());
            //????????????
            cell = row.createCell(5);
            sheet.setColumnWidth(5, 15 * 256);
            cell.setCellValue(result.getEntryTime());
            //????????????
            cell = row.createCell(6);
            cell.setCellValue(result.getTurnoverType());
            //????????????
            cell = row.createCell(7);
            sheet.setColumnWidth(7, 15 * 256);
            cell.setCellValue(result.getResignationTime());
            //????????????
            cell = row.createCell(8);
            sheet.setColumnWidth(8, 20 * 256);
            cell.setCellValue(result.getLeavingReasons());
        }
        //3.????????????

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        new DownloadUtils().download(outputStream,response,month+"????????????.xlsx");
    }

    /**
     * ?????????????????????????????????????????????
     * ????????????-??? -- entryTime??????resignationTime????????????
     */
//    @GetMapping(value = "/export/{month}")
//    public void export(@PathVariable(name = "month") String month) throws Exception {
//        //1.??????????????????
//        List<EmployeeReportResult> list = userCompanyPersonalService.findByReport(companyId, month);
//        //2.????????????
//        Resource resource = new ClassPathResource("excelModel/hr-demo.xlsx");
//        FileInputStream fis = new FileInputStream(resource.getFile());
//
//        //3.???????????????????????????
//        new ExcelExportUtil(EmployeeReportResult.class,2,2).export(response,fis,list,month+"????????????.xlsx");
//
//    }

    /**
     * ????????????pdf??????
     */
    @GetMapping("/{id}/pdf")
    public void createPdf(@PathVariable String id) throws IOException {

        //1.??????jasper??????
        Resource resource = new ClassPathResource("templates/profile.jasper");
        FileInputStream fis = new FileInputStream(resource.getFile());

        ServletOutputStream os = response.getOutputStream();
        try {
            //2.????????????
            //a.??????????????????
            UserCompanyPersonal personal = userCompanyPersonalService.findById(id);
            System.out.println(personal);
            //b.??????????????????
            UserCompanyJobs jobs = userCompanyJobsService.findById(id);
            System.out.println(jobs);
            //c.????????????
//            String staffPhoto = "http://r8opc3p1v.hn-bkt.clouddn.com/"+id;
            //3.??????pdf????????????????????????pdf
            Map parameters = new HashMap<>();

 //           parameters.put("staffPhoto", staffPhoto);
            Map<String,Object> personalMap = BeanMapUtils.beanToMap(personal);
            Map<String,Object> jobsMap = BeanMapUtils.beanToMap(jobs);
            parameters.putAll(personalMap);
            parameters.putAll(jobsMap);

            JasperPrint print = JasperFillManager.fillReport(fis, parameters, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfStream(print, os);
        }catch (JRException ex){
            ex.printStackTrace();
        }finally {
            os.flush();
        }
    }
}
