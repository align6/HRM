package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.ExtraDutyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraDutyConfigDao extends JpaRepository<ExtraDutyConfig,String>, JpaSpecificationExecutor<ExtraDutyConfig> {
    ExtraDutyConfig findByCompanyIdAndDepartmentId(String companyId,String departmentId);
}
