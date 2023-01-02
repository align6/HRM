package com.ihrm.salarys.service;

import com.ihrm.domain.salarys.Settings;
import com.ihrm.salarys.dao.SettingsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {
    @Autowired
    private SettingsDao settingsDao;

    //保存企业设置
    public void save(Settings settings){
        settingsDao.save(settings);
    }

    //根据id获取企业设置
    public Settings findById(String companyId){
        Optional<Settings> optional = settingsDao.findById(companyId);
        return optional.isPresent() ? optional.get() : null;
    }
}
