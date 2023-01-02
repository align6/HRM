package com.ihrm.salarys.dao;

import com.ihrm.domain.salarys.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsDao extends JpaRepository<Settings,String>, JpaSpecificationExecutor<Settings> {
}
