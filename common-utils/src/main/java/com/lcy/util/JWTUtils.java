package com.lcy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT 工具类。
 * 密钥优先取环境变量 JWT_SECRET，未设置时使用开发默认值。
 */
public class JWTUtils {

    private static final String SECRET = System.getenv().getOrDefault("JWT_SECRET", "yun-music-dev-secret");

    /** token 有效期（天） */
    private static final int EXPIRE_DAYS = 3;

    /**
     * 生成 token
     */
    public static String getToken(Map<String, String> payload) {
        JWTCreator.Builder builder = JWT.create();
        payload.forEach(builder::withClaim);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, EXPIRE_DAYS);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 校验 token，签名错误或过期都会抛异常
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }
}
