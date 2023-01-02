package com.ihrm.atte.dao;

import com.ihrm.domain.attendance.entity.CompanySettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanySettingsDao extends JpaRepository<CompanySettings,String>, JpaSpecificationExecutor<CompanySettings> {
}
