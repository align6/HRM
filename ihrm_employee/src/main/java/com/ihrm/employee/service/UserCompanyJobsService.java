package com.ihrm.employee.service;

import com.ihrm.domain.employee.UserCompanyJobs;
import com.ihrm.employee.dao.UserCompanyJobsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCompanyJobsService {

    @Autowired
    private UserCompanyJobsDao userCompanyJobsDao;

    /**
     * 保存员工岗位信息表
     */
    public void save(UserCompanyJobs jobsInfo){
        userCompanyJobsDao.save(jobsInfo);
    }

    /**
     * 根据userId查询员工岗位信息表
     */
    public UserCompanyJobs findById(String userId){
        return userCompanyJobsDao.findByUserId(userId);
    }
}
