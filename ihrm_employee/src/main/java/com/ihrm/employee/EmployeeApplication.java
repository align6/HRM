package com.ihrm.employee;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.ihrm")
@EntityScan("com.ihrm.domain.employee")
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    @Bean
    public JwtUtils jwtUtils(){
        return new JwtUtils();
    }
}
