package com.ihrm.salarys.client;

import com.ihrm.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ihrm-system")
public interface UserFeignClient {

    @GetMapping("/system/user/{id}")
    Result findById(@PathVariable String id);
}
