package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.AttendanceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceConfigDao extends JpaRepository<AttendanceConfig,String>, JpaSpecificationExecutor<AttendanceConfig> {

    AttendanceConfig findByCompanyIdAndDepartmentId(String companyId,String departmentId);
}
