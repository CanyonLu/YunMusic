package com.lcy.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcy.util.JWTUtils;
import com.lcy.util.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 需要登录的接口统一在此校验 token，校验通过后把用户信息放入 request attribute。
 * 业务侧通过 {@link #currentUserId(HttpServletRequest)} 取用户 id，不再自己解析 token。
 */
public class JwtAuthInterceptor implements org.springframework.web.servlet.HandlerInterceptor {

    public static final String ATTR_USER_ID = "userId";
    public static final String ATTR_NICKNAME = "nickname";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("token");
        if (token == null || token.trim().isEmpty()) {
            reject(response, "未登录");
            return false;
        }
        try {
            DecodedJWT jwt = JWTUtils.verify(token);
            request.setAttribute(ATTR_USER_ID, jwt.getClaim("id").asString());
            request.setAttribute(ATTR_NICKNAME, jwt.getClaim("nickname").asString());
            return true;
        } catch (TokenExpiredException e) {
            reject(response, "登录已过期，请重新登录");
        } catch (SignatureVerificationException | AlgorithmMismatchException | InvalidClaimException e) {
            reject(response, "token 无效");
        } catch (Exception e) {
            reject(response, "token 无效");
        }
        return false;
    }

    /** 取当前登录用户 id（需先经过本拦截器） */
    public static String currentUserId(HttpServletRequest request) {
        Object id = request.getAttribute(ATTR_USER_ID);
        return id == null ? null : id.toString();
    }

    private void reject(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(AjaxResult.error(msg)));
    }
}
