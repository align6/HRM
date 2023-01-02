package com.ihrm.salarys.client;

import com.ihrm.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ihrm-attendance")
public interface AttendanceFeignClient {

    @GetMapping("/atte/archive/{userId}/{yearMonth}")
    Result historyData(@PathVariable String userId, @PathVariable String yearMonth);
}
