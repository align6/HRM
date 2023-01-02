package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.DayOffConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DayOffConfigDao extends JpaRepository<DayOffConfig,String>, JpaSpecificationExecutor<DayOffConfig> {
    DayOffConfig findByCompanyIdAndDepartmentId(String companyId,String departmentId);
}
