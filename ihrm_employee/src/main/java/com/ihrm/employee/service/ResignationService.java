package com.ihrm.employee.service;

import com.ihrm.domain.employee.EmployeeResignation;
import com.ihrm.employee.dao.ResignationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResignationService {

    @Autowired
    private ResignationDao resignationDao;

    /**
     * 保存离职申请表
     */
    public void save(EmployeeResignation resignation){
        resignation.setCreateTime(new Date());
        resignationDao.save(resignation);
    }

    /**
     * 根据userId查询离职申请表
     */
    public EmployeeResignation findById(String userId){
        return resignationDao.findByUserId(userId);
    }
}
