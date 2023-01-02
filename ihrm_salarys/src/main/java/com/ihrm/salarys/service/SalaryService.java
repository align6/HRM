package com.ihrm.salarys.service;

import com.ihrm.common.entity.PageResult;
import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.salarys.Settings;
import com.ihrm.domain.salarys.UserSalary;
import com.ihrm.domain.salarys.vo.UserSalaryVo;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import com.ihrm.domain.social_securitys.UserSocialSecurity;
import com.ihrm.domain.system.User;
import com.ihrm.salarys.client.FeignClientService;
import com.ihrm.salarys.dao.SettingsDao;
import com.ihrm.salarys.dao.UserSalaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private UserSalaryDao userSalaryDao;

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private FeignClientService feignClientService;

    //保存或更新工资
    public void saveUserSalary(UserSalary userSalary){
        userSalaryDao.save(userSalary);
    }

    //查询用户工资
    public UserSalary findUserSalary(String userId){
        Optional<UserSalary> optional = userSalaryDao.findById(userId);
        return optional.isPresent() ? optional.get() : null;
    }

    //分页查询当月工资列表
    public PageResult findAll(Integer page,Integer size,String companyId){
        Page result = userSalaryDao.findPage(companyId, PageRequest.of(page-1,size));
        return new PageResult(result.getTotalElements(), result.getContent());
    }

    //获取用户工资明细
    public UserSalaryVo getDetail(String userId, String yearMonth, String companyId) {
        UserSalaryVo userSalaryVo = new UserSalaryVo();
        User user = feignClientService.getUserInfo(userId);
        userSalaryVo.setUser(user);
        UserSalary userSalary = userSalaryDao.findById(userId).get();
        userSalaryVo.setUserSalary(userSalary);
        Settings settings = settingsDao.findById(companyId).get();
        userSalaryVo.setSettings(settings);
        ArchiveDetail archiveDetail = feignClientService.getSocialInfo(userId,yearMonth);
        userSalaryVo.setArchiveDetail(archiveDetail);
        ArchiveMonthlyInfo archiveMonthlyInfo = feignClientService.getAtteInfo(userId,yearMonth);
        userSalaryVo.setArchiveMonthlyInfo(archiveMonthlyInfo);
        return userSalaryVo;
    }
}
