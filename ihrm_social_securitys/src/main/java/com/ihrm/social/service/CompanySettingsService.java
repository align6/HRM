package com.ihrm.social.service;

import com.ihrm.domain.social_securitys.CompanySettings;
import com.ihrm.social.dao.CompanySettingsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanySettingsService {
    @Autowired
    private CompanySettingsDao companySettingsDao;

    /**
     * 根据企业id查询
     */
    public CompanySettings findById(String companyId){
        Optional<CompanySettings> settings = companySettingsDao.findById(companyId);
        return settings.isPresent()? settings.get() : null;
    }

    public void save(CompanySettings companySettings) {
        companySettings.setIsSettings(1); //已完成当月设置
        companySettingsDao.save(companySettings);
    }
}
