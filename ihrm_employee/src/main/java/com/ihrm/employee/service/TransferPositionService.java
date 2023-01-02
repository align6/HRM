package com.ihrm.employee.service;

import com.ihrm.domain.employee.EmployeeTransferPosition;
import com.ihrm.employee.dao.TransferPositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransferPositionService {

    @Autowired
    private TransferPositionDao transferPositionDao;

    /**
     * 保存调岗申请表
     */
    public void save(EmployeeTransferPosition transferPosition){
        transferPosition.setCreateTime(new Date());
        transferPosition.setEstatus(1);
        transferPositionDao.save(transferPosition);
    }

    /**
     * 根据userId查询调岗申请表
     */
    public EmployeeTransferPosition findById(String userId){
        return transferPositionDao.findByUserId(userId);
    }

    /**
     * 根据userId和status查询调岗申请表
     */
    public EmployeeTransferPosition findById(String userId, Integer status){
        EmployeeTransferPosition transferPosition = transferPositionDao.findById(userId).get();
        if (status!=null && transferPosition!=null){
            if (transferPosition.getEstatus()!=status){
                transferPosition = null;
            }
        }
        return transferPosition;
    }
}
