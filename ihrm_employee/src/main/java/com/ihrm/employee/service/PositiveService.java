package com.ihrm.employee.service;

import com.ihrm.domain.employee.EmployeePositive;
import com.ihrm.employee.dao.PositiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PositiveService {

    @Autowired
    private PositiveDao positiveDao;

    /**
     * 根据userId和status查询转正申请表
     */
    public EmployeePositive findById(String userId, Integer status){
        EmployeePositive positive = positiveDao.findById(userId).get();
        if (status!=null && positive!=null){
            if (positive.getStatus()!=status){
                positive = null;
            }
        }
        return positive;
    }

    /**
     * 根据userId查询转正申请表
     */
    public EmployeePositive findById(String userId){
        return positiveDao.findByUserId(userId);
    }


    /**
     * 保存转正申请表
     */
    public void save(EmployeePositive positive){
        positive.setCreateTime(new Date());
        positive.setStatus(1);
        positiveDao.save(positive);
    }
}
