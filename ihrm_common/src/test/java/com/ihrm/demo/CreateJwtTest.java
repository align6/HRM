package com.ihrm.demo;

import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateJwtTest {
    /**
     * 通过jjwt创建token
     */
    public static void main(String[] args) {
//        JwtBuilder jwtBuilder = Jwts.builder().setId("888")
//                .setSubject("张三")
//                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode("ihrm"))
//                .claim("companyId","东莞理工");
//        String token = jwtBuilder.compact();
//        System.out.println(token);
        JwtUtils jwtUtils = new JwtUtils();
        Map<String,Object> map = new HashMap<>();
        map.put("companyId","1");
        map.put("companyName","东莞理工");
        String token = jwtUtils.createJwt("1491682702800265216","nainai",map);
        System.out.println(token);
    }
}
