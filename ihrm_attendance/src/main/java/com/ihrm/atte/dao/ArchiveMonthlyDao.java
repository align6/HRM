package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.ArchiveMonthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveMonthlyDao extends JpaRepository<ArchiveMonthly,String>, JpaSpecificationExecutor<ArchiveMonthly> {
    List<ArchiveMonthly> findByCompanyIdAndArchiveYear(String companyId,String archiveYear);

    ArchiveMonthly findByCompanyIdAndArchiveYearAndAndArchiveMonth(String companyId,String year,String month);
}
