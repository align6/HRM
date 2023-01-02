package com.ihrm.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt.config")  //不能配toString（）方法，否则无法在application.yml中配置
public class JwtUtils {
    private String key;  //签名私钥

    private Long ttl;  //签名持续时间

    /**
     * 设置认证token
     *  id：登录用户id
     *  name：登录用户名
     */
    public String createJwt(String id, String name, Map<String,Object> map){
        //设置失效时间
        long now = System.currentTimeMillis();  //当前毫秒数
        long exp = now+ttl;  //当前时间+持久生效时间
        //创建jwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key);
        //根据map设置claims
        for (Map.Entry<String, Object> entry : map.entrySet()){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }
        jwtBuilder.setExpiration(new Date(exp));
        //创建token
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析token获取字符串
     */
    public Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
