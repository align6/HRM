package com.ihrm.system;

import com.ihrm.common.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * 拦截器配置
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/system/login","/frame/register/**");//登录和注册不需要获取用户信息
    }
}
