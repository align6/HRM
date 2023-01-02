package com.ihrm.salarys.service;

import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.salarys.SalaryArchive;
import com.ihrm.domain.salarys.SalaryArchiveDetail;
import com.ihrm.domain.salarys.Settings;
import com.ihrm.domain.salarys.UserSalary;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import com.ihrm.salarys.client.FeignClientService;
import com.ihrm.salarys.dao.SalaryArchiveDao;
import com.ihrm.salarys.dao.SalaryArchiveDetailDao;
import com.ihrm.salarys.dao.UserSalaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArchiveService {

    @Autowired
    private SalaryArchiveDao salaryArchiveDao;

    @Autowired
    private SalaryArchiveDetailDao salaryArchiveDetailDao;

    @Autowired
    private FeignClientService feignClientService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private UserSalaryDao userSalaryDao;

    @Autowired
    private SalaryService salaryService;

    //根据企业id和年月查询归档
    public SalaryArchive findSalaryArchive(String yearMonth, String companyId) {
        return salaryArchiveDao.findByCompanyIdAndYearsMonth(companyId,yearMonth);
    }

    //根据归档id查询归档明细
    public List<SalaryArchiveDetail> findSalaryArchiveDetail(String id) {
        return salaryArchiveDetailDao.findByArchiveId(id);
    }

    //查询月报表数据
    public List<SalaryArchiveDetail> getReports(String yearMonth, String companyId) {
        List<SalaryArchiveDetail> list = new ArrayList<>();
        //获取当前企业的福利津贴
        Settings settings = settingsService.findById(companyId);
        //查询所有用户
        Page<Map> page = userSalaryDao.findPage(companyId,null);
        //遍历用户数据
        for (Map map : page.getContent()){
            //构造salaryArchiveDetail
            SalaryArchiveDetail detail = new SalaryArchiveDetail();
            detail.setUser(map);
            //获取每个用户的社保数据
            ArchiveDetail socialInfo = feignClientService.getSocialInfo(detail.getUserId(),yearMonth);
            detail.setSocialInfo(socialInfo);
            //获取每个用户的考勤数据
            ArchiveMonthlyInfo atteInfo = feignClientService.getAtteInfo(detail.getUserId(),yearMonth);
            detail.setAtteInfo(atteInfo);
            //获取每个用的的工资数据
            UserSalary userSalary = salaryService.findUserSalary(detail.getUserId());
            detail.setUserSalary(userSalary);
            //计算工资
            detail.calSalary(settings);
            list.add(detail);
        }
        return list;
    }

    /**
     * 通过年份查询一整年的历史归档
     */
    public List<SalaryArchive> findArchiveByYear(String companyId, String year) {
        return salaryArchiveDao.findByCompanyIdAndYearsMonthLike(companyId,year+"%");
    }
}
