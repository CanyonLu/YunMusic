package com.lcy.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcy.entity.UserEntity;
import com.lcy.mapper.IndexMapper;
import com.lcy.util.JWTUtils;
import com.lcy.util.domain.AjaxResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class IndexService {

    private static final ObjectMapper JSON = new ObjectMapper();

    private final IndexMapper indexMapper;

    public IndexService(IndexMapper indexMapper) {
        this.indexMapper = indexMapper;
    }

    /**
     * 注册前校验账号是否可用
     */
    public AjaxResult checkUserInfo(String params) {
        UserEntity user = parse(params);
        if (user == null || isBlank(user.getAccount())) {
            return AjaxResult.error("账号不能为空");
        }
        return indexMapper.checkAccountExist(user.getAccount()) == 0
                ? AjaxResult.success()
                : AjaxResult.error("该账号已被注册");
    }

    /**
     * 用户注册
     */
    public AjaxResult userRegister(String params) {
        UserEntity user = parse(params);
        if (user == null || isBlank(user.getAccount()) || isBlank(user.getPassword())) {
            return AjaxResult.error("账号和密码不能为空");
        }
        if (indexMapper.checkAccountExist(user.getAccount()) > 0) {
            return AjaxResult.error("该账号已被注册");
        }
        user.setId(UUID.randomUUID().toString());
        if (indexMapper.userRegister(user) != 1) {
            return AjaxResult.error("注册失败，请重试");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("token", tokenOf(user));
        data.put("nickname", user.getNickname());
        return AjaxResult.success(data);
    }

    /**
     * 用户登录
     */
    public AjaxResult userLogin(UserEntity user) {
        if (user == null || isBlank(user.getAccount()) || isBlank(user.getPassword())) {
            return AjaxResult.error("账号和密码不能为空");
        }
        UserEntity found = indexMapper.queryUserByAccountAndPassword(user);
        if (found == null) {
            return AjaxResult.error("账号或密码错误，请重试！");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("token", tokenOf(found));
        data.put("nickname", found.getNickname());
        return AjaxResult.success(data);
    }

    /**
     * 校验登录状态：用 token 里的 id 查库，收藏数据实时取，不再塞进 JWT
     */
    public Map<String, Object> checkUserLoginState(String token) {
        Map<String, Object> result = new LinkedHashMap<>();
        if (isBlank(token)) {
            return notLogin(result);
        }
        try {
            DecodedJWT jwt = JWTUtils.verify(token);
            UserEntity user = indexMapper.queryUserById(jwt.getClaim("id").asString());
            if (user == null) {
                return notLogin(result);
            }
            result.put("state", true);
            result.put("msg", "请求成功");
            result.put("id", user.getId());
            result.put("nickname", user.getNickname());
            result.put("songlist", nullToEmpty(user.getSonglist()));
            result.put("songs", nullToEmpty(user.getSongs()));
            return result;
        } catch (Exception e) {
            return notLogin(result);
        }
    }

    public AjaxResult editUserInfo(UserEntity user) {
        if (user == null || isBlank(user.getId())) {
            return AjaxResult.error("用户不存在");
        }
        return indexMapper.editUserInfo(user) == 1 ? AjaxResult.success() : AjaxResult.error();
    }

    private Map<String, Object> notLogin(Map<String, Object> result) {
        result.put("state", false);
        result.put("msg", "未登录或登录已过期");
        return result;
    }

    /** JWT 中只放 id 与昵称，避免可变数据被固化在 token 里 */
    private String tokenOf(UserEntity user) {
        Map<String, String> payload = new HashMap<>();
        payload.put("id", user.getId());
        payload.put("nickname", user.getNickname());
        return JWTUtils.getToken(payload);
    }

    private UserEntity parse(String params) {
        if (isBlank(params)) {
            return null;
        }
        try {
            return JSON.readValue(params, UserEntity.class);
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
