package com.ihrm.atte.service;

import com.ihrm.atte.dao.*;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.attendance.entity.ArchiveMonthly;
import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.attendance.entity.CompanySettings;
import com.ihrm.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArchiveService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AttendanceDao attendanceDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArchiveMonthlyInfoDao archiveMonthlyInfoDao;

    @Autowired
    private ArchiveMonthlyDao archiveMonthlyDao;

    @Autowired
    private CompanySettingsDao companySettingsDao;


    /**
     * 查询归档数据
     */
    public List<ArchiveMonthlyInfo> getReports(String companyId, String atteDate) {
        //查询所有企业用户
        List<User> userList = userDao.findByCompanyId(companyId);
        //循环用户列表，统计每个用户当月的考勤记录
        List<ArchiveMonthlyInfo> infoList = new ArrayList<>();
        for (User user : userList){
            ArchiveMonthlyInfo info = new ArchiveMonthlyInfo(user);
            Map map = attendanceDao.statisticByUser(user.getId(),atteDate+"%");
            info.setStatisticData(map);
            infoList.add(info);
        }
        return infoList;
    }

    /**
     * 将考勤数据存入历史归档中
     */
    public void saveArchive(String archiveDate, String companyId) {
        //保存归档主表数据
        ArchiveMonthly archiveMonthly = new ArchiveMonthly();
        archiveMonthly.setId(idWorker.nextId()+"");
        archiveMonthly.setCompanyId(companyId);
        archiveMonthly.setArchiveYear(archiveDate.substring(0,4));
        archiveMonthly.setArchiveMonth(archiveDate.substring(5));

        //查询所有企业用户
        List<User> userList = userDao.findByCompanyId(companyId);
        //循环用户列表，统计每个用户当月的考勤记录
        for (User user : userList){
            ArchiveMonthlyInfo info = new ArchiveMonthlyInfo(user);
            Map map = attendanceDao.statisticByUser(user.getId(),archiveDate+"%");
            info.setStatisticData(map);
            info.setId(idWorker.nextId()+"");
            info.setAtteArchiveMonthlyId(archiveMonthly.getId());
            info.setArchiveDate(archiveDate);
            archiveMonthlyInfoDao.save(info);
        }

        //总人数
        archiveMonthly.setTotalPeopleNum(userList.size());
        archiveMonthly.setFullAttePeopleNum(userList.size());
        archiveMonthlyDao.save(archiveMonthly);
    }

    /**
     * 判断是否已经归档
     */
    public ArchiveMonthly findArchive(String companyId, String yearsMonth) {
        String year = yearsMonth.substring(0,4);
        String month = yearsMonth.substring(5);
        return archiveMonthlyDao.findByCompanyIdAndArchiveYearAndAndArchiveMonth(companyId,year,month);
    }

    /**
     * 新建归档报表
     */
    public void newReports(String yearMonth, String companyId) {
        CompanySettings companySettings = companySettingsDao.findById(companyId).get();
        companySettings.setDataMonth(yearMonth);
        companySettingsDao.save(companySettings);
    }

    /**
     * 根据年份查看历史归档
     */
    public List<ArchiveMonthly> findHistoryReports(String year, String companyId) {
        return archiveMonthlyDao.findByCompanyIdAndArchiveYear(companyId,year);
    }

    /**
     * 根据历史归档id查看明细
     */
    public List<ArchiveMonthlyInfo> findHistoryReportDetail(String id) {
        return archiveMonthlyInfoDao.findByAtteArchiveMonthlyId(id);
    }

    /**
     * 根据用户id和年月查询归档明细
     */
    public ArchiveMonthlyInfo findUserHistoryData(String userId, String yearMonth) {
        return archiveMonthlyInfoDao.findByUserIdAndArchiveDate(userId,yearMonth);
    }
}
