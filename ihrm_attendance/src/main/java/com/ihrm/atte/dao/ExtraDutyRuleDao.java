package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.ExtraDutyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraDutyRuleDao extends JpaRepository<ExtraDutyRule,String>, JpaSpecificationExecutor<ExtraDutyRule> {
    List<ExtraDutyRule> findByExtraDutyConfigId(String extraDutyConfigId);

    Integer deleteByExtraDutyConfigId(String extraDutyConfigId);

    List<ExtraDutyRule> findByCompanyIdAndDepartmentId(String companyId,String departmentId);
}
