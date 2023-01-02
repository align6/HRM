package com.ihrm.salarys.dao;

import com.ihrm.domain.salarys.SalaryArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryArchiveDao extends JpaRepository<SalaryArchive,String>, JpaSpecificationExecutor<SalaryArchive> {
    SalaryArchive findByCompanyIdAndYearsMonth(String companyId,String yearsMonth);

    List<SalaryArchive> findByCompanyIdAndYearsMonthLike(String companyId, String yearsMonth);
}
