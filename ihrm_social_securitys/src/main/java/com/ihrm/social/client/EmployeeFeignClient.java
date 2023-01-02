package com.ihrm.social.client;

import com.ihrm.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ihrm-employee")
public interface EmployeeFeignClient {
    @GetMapping("/employees/{id}/personal")
    Result findPersonalInfo(@PathVariable String id);
}
