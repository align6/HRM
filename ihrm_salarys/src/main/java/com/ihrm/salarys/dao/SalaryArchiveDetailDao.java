package com.ihrm.salarys.dao;

import com.ihrm.domain.salarys.SalaryArchiveDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryArchiveDetailDao extends JpaRepository<SalaryArchiveDetail,String>, JpaSpecificationExecutor<SalaryArchiveDetail> {
    List<SalaryArchiveDetail> findByArchiveId(String archiveId);
}
