package com.ihrm.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;

public class ParseJwtTest {
    /**
     * 解析jwt Token字符串
     */
    public static void main(String[] args) {
        String token = "\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlvKDkuIkiLCJpYXQiOjE2NDUwNjYzMzksImNvbXBhbnlJZCI6IuS4nOiOnueQhuW3pSJ9.vEaLwF6y3F2VZeRw96r90Jjq6XsJZ_cT24PmG746m50";
        Claims claims = Jwts.parser().setSigningKey(TextCodec.BASE64.encode("ihrm")).parseClaimsJws(token).getBody();
        //常规输出
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
        //自定义输出
        System.out.println("companyId:"+claims.get("companyId"));
    }
}
