package com.ihrm.salarys.client;

import com.alibaba.fastjson.JSON;
import com.ihrm.common.entity.Result;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.attendance.entity.ArchiveMonthlyInfo;
import com.ihrm.domain.social_securitys.ArchiveDetail;
import com.ihrm.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeignClientService {
    @Autowired
    private AttendanceFeignClient attendanceFeignClient;

    @Autowired
    private SocialSecurityFeignClient socialSecurityFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private IdWorker idWorker;

    public User getUserInfo(String userId){
        Result result = userFeignClient.findById(userId);
        User user = null;
        if (result.isSuccess()){
            user = JSON.parseObject(JSON.toJSONString(result.getData()),User.class);
        }
        return user;
    }

    public ArchiveMonthlyInfo getAtteInfo(String userId, String yearMonth){
        Result result = attendanceFeignClient.historyData(userId, yearMonth);
        ArchiveMonthlyInfo info = null;
        if (result.isSuccess()){
            info = JSON.parseObject(JSON.toJSONString(result.getData()),ArchiveMonthlyInfo.class);
        }
        return info;
    }

    public ArchiveDetail getSocialInfo(String userId, String yearMonth){
        Result result = socialSecurityFeignClient.historyData(userId, yearMonth);
        ArchiveDetail info = null;
        if (result.getData() != null){
            info = JSON.parseObject(JSON.toJSONString(result.getData()),ArchiveDetail.class);
        }
        return info;
    }
}
