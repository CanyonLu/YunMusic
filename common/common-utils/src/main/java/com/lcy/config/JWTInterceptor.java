package com.lcy.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcy.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lucy
 * @date Created in 2022/12/11 20:55
 */
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        log.info("拦截器：token：" + token);
        Map<String, Object> map = new HashMap<>();
        try{
            JWTUtils.verify(token);
            return true;
        }catch (SignatureVerificationException e){
            map.put("msg", "签名不一致");
        }catch (TokenExpiredException e){
            map.put("msg", "令牌过期");
        }catch (AlgorithmMismatchException e){
            map.put("msg", "算法不匹配");
        }catch (InvalidClaimException e){
            map.put("msg", "失效的payload");
        }catch (Exception e){
            map.put("msg", "token无效");
        }
        map.put("state", false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
