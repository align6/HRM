package com.ihrm.social.dao;

import com.ihrm.domain.social_securitys.ArchiveDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveDetailDao extends JpaRepository<ArchiveDetail,String>, JpaSpecificationExecutor<ArchiveDetail> {
    void deleteByArchiveId(String archiveId);

    List<ArchiveDetail> findByArchiveId(String archiveId);

    ArchiveDetail findByUserIdAndYearsMonth(String userId, String yearsMonth);
}
