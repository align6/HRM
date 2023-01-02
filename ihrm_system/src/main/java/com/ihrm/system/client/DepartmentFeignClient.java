package com.ihrm.system.client;

import com.ihrm.common.entity.Result;
import com.ihrm.domain.company.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 声明接口，通过feign调用其他微服务
 */
@FeignClient(value = "ihrm-company")
public interface DepartmentFeignClient {
    /**
     * 调用微服务的接口，用于测试
     */
    @GetMapping("/company/department/{id}")
    Result findById(@PathVariable String id);

    /**
     * 调用部门编码和企业id查询
     */
    @PostMapping("/company/department/search")
    Department findByCode(@RequestParam String code, @RequestParam String companyId);
}
