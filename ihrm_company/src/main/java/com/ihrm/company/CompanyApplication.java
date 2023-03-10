package com.ihrm.company;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm")
//2.配置jpa注解的扫描
@EntityScan(value = "com.ihrm.domain.company")
//3.注册到eureka
@EnableEurekaClient
public class CompanyApplication {
    /**
     *启动方法
     */
    public static void main(String args[]){
        SpringApplication.run(CompanyApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }

    /**
     * 如果没使用OpenEntityManagerInViewFilter，session会在service.find()方法后就被关闭，用了以后session在整个view层结束后才关闭。
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
        return new OpenEntityManagerInViewFilter();
    }
}
