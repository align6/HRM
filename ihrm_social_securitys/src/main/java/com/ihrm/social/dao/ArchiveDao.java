package com.ihrm.social.dao;

import com.ihrm.domain.social_securitys.Archive;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveDao extends JpaRepository<Archive,String>, JpaSpecificationExecutor<Archive> {
    Archive findByCompanyIdAndAndYearsMonth(String companyId, String yearsMonth);

    List<Archive> findByCompanyIdAndYearsMonthLike(String companyId, String yearsMonth);
}
