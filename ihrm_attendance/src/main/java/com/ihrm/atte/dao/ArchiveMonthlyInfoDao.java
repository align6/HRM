package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveMonthlyInfoDao extends JpaRepository<ArchiveMonthlyInfo,String>, JpaSpecificationExecutor<ArchiveMonthlyInfo> {
    List<ArchiveMonthlyInfo> findByAtteArchiveMonthlyId(String atteArchiveMonthlyId);

    ArchiveMonthlyInfo findByUserIdAndArchiveDate(String userId, String archiveDate);
}
