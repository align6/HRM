package com.ihrm.company.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "ihrm-system")
public interface UserFeignClient {
    /**
     * 通过用户名查询用户id
     */
    @GetMapping("/system/user/name")
    String findIdByName(String username);
}
