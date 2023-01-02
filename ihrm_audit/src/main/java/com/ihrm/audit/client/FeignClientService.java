package com.ihrm.audit.client;

import com.alibaba.fastjson.JSON;
import com.ihrm.common.entity.Result;
import com.ihrm.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FeignClientService {

    @Autowired
    private SystemFeignClient systemFeignClient;

    public User getUserById(String userId){
        if (StringUtils.isEmpty(userId)){
            throw new RuntimeException("当前用户为空！");
        }
        Result result = systemFeignClient.findById(userId);
        if (!result.isSuccess()){
            throw new RuntimeException("用户ID不存在，请联系管理员：" + userId);
        }
        return JSON.parseObject(JSON.toJSONString(result.getData()), User.class);
    }
}
