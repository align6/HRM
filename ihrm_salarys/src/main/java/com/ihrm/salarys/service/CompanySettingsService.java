package com.ihrm.salarys.service;

import com.ihrm.domain.salarys.CompanySettings;
import com.ihrm.salarys.dao.CompanySettingsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanySettingsService {

    @Autowired
    private CompanySettingsDao companySettingsDao;

    //根据公司id查询公司设置
    public CompanySettings findById(String companyId) {
        Optional<CompanySettings> optional = companySettingsDao.findById(companyId);
        return optional.isPresent() ? optional.get() : null;
    }

    //保存设置
    public void save(CompanySettings companySettings) {
        companySettings.setIsSettings(1);
        companySettingsDao.save(companySettings);
    }

    //创建新报表
    public void newReport(String companyId, String yearMonth) {
        CompanySettings companySettings = companySettingsDao.findById(companyId).get();
        companySettings.setDataMonth(yearMonth);
        companySettingsDao.save(companySettings);
    }
}
