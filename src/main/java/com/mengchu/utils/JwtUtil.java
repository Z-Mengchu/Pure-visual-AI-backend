package com.mengchu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static String signKey = "mengchu";//签名密钥
    private static Long expire = 172800000L;//过期时间（48）小时


    /**
     * 生成jwt令牌
     *
     * @param claims：载荷
     * @return ：jwt令牌
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signKey)//签名算法，密钥
                .setClaims(claims)//自定义内容（载荷）
                .setExpiration(new Date(System.currentTimeMillis() + expire))//过期时间
                .compact();
        return jwt;
    }

    /**
     * 解析jwt令牌
     *
     * @param jwt：需要解析的jwt
     * @return : jwt第二部分payload中存储的内容
     */
    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
