package com.lcy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author lucy
 * @date Created in 2022/12/11 20:37
 */
public class JWTUtils {
    private static String SECRET = "mySecret";

    /**
     * 获取token
     * @param map
     * @return
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE , 3);
        //设置过期时间
        builder.withExpiresAt(instance.getTime());
        String token = builder.sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 验证token,如果数据被修改或过期都会抛出异常，没有异常返回payload
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }
}
