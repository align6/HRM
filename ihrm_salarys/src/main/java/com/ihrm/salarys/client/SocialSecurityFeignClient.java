package com.ihrm.salarys.client;

import com.ihrm.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ihrm-social-securitys")
public interface SocialSecurityFeignClient {
    @GetMapping("/social_securitys/historys/archiveDetail/{userId}/{yearMonth}")
    Result historyData(@PathVariable String userId, @PathVariable String yearMonth);
}
