package com.ihrm.social;

import com.ihrm.common.shiro.realm.IhrmRealm;
import com.ihrm.common.shiro.session.CustomSessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    //创建realm
    @Bean
    public IhrmRealm getRealm(){
        return new IhrmRealm();
    }

    //创建安全管理器
    @Bean
    public SecurityManager getSecurityManager(IhrmRealm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        //将自定义的会话管理器注册到安全管理器
        securityManager.setSessionManager(sessionManager());
        //将自定义的redis缓存管理器注册到安全管理器
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    //配置shiro的过滤器工厂
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        //1.创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //2.设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //3.通用配置
        filterFactory.setLoginUrl("/authError?code=1");
        filterFactory.setUnauthorizedUrl("/authError?code=2");
        //4.设置过滤器集合
        Map<String,String> filterMap = new LinkedHashMap<>();
        //anon--匿名访问
        //登录
        filterMap.put("/system/login","anon");
        //注册

        filterMap.put("/authError","anon");
        //authc--认证之后才能访问
        //filterMap.put("/**","authc");
        //perms--具有某种权限

        filterFactory.setFilterChainDefinitionMap(filterMap);

        return filterFactory;
    }

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    //配置redis的控制器
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);

        return redisManager;
    }

    //配置sessionDao
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());

        return sessionDAO;
    }

    //配置会话管理器
    public DefaultWebSessionManager sessionManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionIdCookieEnabled(false);//禁用cookie
        sessionManager.setSessionIdUrlRewritingEnabled(false);//禁用url重写
        sessionManager.setSessionDAO(redisSessionDAO());

        return sessionManager;
    }

    //配置缓存管理器
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        return redisCacheManager;
    }

    //开启对shiro注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
