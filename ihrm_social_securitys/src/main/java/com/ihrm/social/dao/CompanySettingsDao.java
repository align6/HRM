package com.ihrm.social.dao;

import com.ihrm.domain.social_securitys.CompanySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySettingsDao extends JpaRepository<CompanySettings,String>, JpaSpecificationExecutor<CompanySettings> {
}
