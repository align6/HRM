package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.DeductionDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeductionDictDao extends JpaRepository<DeductionDict,String>, JpaSpecificationExecutor<DeductionDict> {
    List<DeductionDict> findByCompanyIdAndDepartmentId(String companyId,String departmentId);

    DeductionDict findByCompanyIdAndDepartmentIdAndDedTypeCode(String companyId,String departmentId,String dedTypeCode);
}
