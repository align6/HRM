package com.ihrm.social.service;

import com.ihrm.common.entity.PageResult;
import com.ihrm.domain.social_securitys.UserSocialSecurity;
import com.ihrm.social.dao.UserSocialSecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSocialSecurityService {
    @Autowired
    private UserSocialSecurityDao userSocialSecurityDao;

    /**
     * 分页查询用户的社保数据
     */
    public PageResult findAll(Integer page, Integer size, String companyId) {
        Page page1 = userSocialSecurityDao.findPage(companyId, PageRequest.of(page-1, size));
        PageResult pageResult = new PageResult(page1.getTotalElements(),page1.getContent());
        return pageResult;
    }

    /**
     * 根据id查询社保情况
     */
    public UserSocialSecurity findById(String id) {
        Optional<UserSocialSecurity> optional = userSocialSecurityDao.findById(id);
        return optional.isPresent()? optional.get() : null;
    }

    /**
     * 更新用户社保
     */
    public void update(UserSocialSecurity userSocialSecurity) {
        userSocialSecurityDao.save(userSocialSecurity);
    }
}
