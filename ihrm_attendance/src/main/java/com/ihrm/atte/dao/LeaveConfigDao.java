package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.LeaveConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveConfigDao extends JpaRepository<LeaveConfig,String>, JpaSpecificationExecutor<LeaveConfig> {
    List<LeaveConfig> findByCompanyIdAndDepartmentId(String companyId,String departmentId);

    LeaveConfig findByCompanyIdAndDepartmentIdAndLeaveType(String companyId,String departmentId,String leaveType);
}
