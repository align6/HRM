package com.ihrm.employee.service;

import com.ihrm.domain.employee.UserCompanyPersonal;
import com.ihrm.domain.employee.response.EmployeeReportResult;
import com.ihrm.employee.dao.UserCompanyPersonalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCompanyPersonalService {

    @Autowired
    private UserCompanyPersonalDao userCompanyPersonalDao;

    /**
     * 保存员工详细信息表
     */
    public void save(UserCompanyPersonal personalInfo){
        userCompanyPersonalDao.save(personalInfo);
    }

    /**
     * 根据userId查询保存员工详细信息表
     */
    public UserCompanyPersonal findById(String userId){
        return userCompanyPersonalDao.findByUserId(userId);
    }

    /**
     * 根据公司和月份生成当月的人事报表
     */
    public List<EmployeeReportResult> findByReport(String companyId, String month) {
        return userCompanyPersonalDao.findByReport(companyId, month+"%");
    }
}
