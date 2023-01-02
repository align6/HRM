package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonExceptionHandler;
import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 *      preHandle:进入控制器方法前执行的方法
 *          return boolean:
 *              true:可以继续执行控制器方法
 *              false：拦截
 *      postHandle:执行控制器方法后执行的方法
 *      afterCompletion:响应结束之前执行的方法
 *
 * 1.简化获取token数据的代码编写
 *      统一的用户权限校验（是否登录）
 * 2.判断用户是否具有当前访问接口的权限
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    /**
     * 简化获取token数据的代码编写
     *      1.通过request获取token信息
     *      2.解析token并获取claims
     *      3.将claims绑定到request域中
     */

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //通过request获取请求头
        String authorization = request.getHeader("Authorization");
        //判断请求头是否为空，或者是否以Bearer开头
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")){
            //获取token
            String token = authorization.replace("Bearer ","");
            //解析token获取claims
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null){
                //通过claims获取到当前用户的可访问api权限
                String apis = (String) claims.get("apis");
                HandlerMethod method = (HandlerMethod) handler;
                //通过handler获取requestMapping的注解
                RequestMapping annotation = method.getMethodAnnotation(RequestMapping.class);
                //获得注解里的name
                String name = annotation.name();
                //判断当前用户是否拥有相应的请求权限
                if (apis.contains(name)){
                    request.setAttribute("user_claims",claims);
                    return true;
                }else {
                    throw new CommonExceptionHandler(ResultCode.UNAUTHORISE);
                }
            }
        }
        throw new CommonExceptionHandler(ResultCode.UNAUTHENTICATED);
    }
}
