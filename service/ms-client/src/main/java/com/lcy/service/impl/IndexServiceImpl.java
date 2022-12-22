package com.lcy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lcy.entity.UserEntity;
import com.lcy.mapper.IndexMapper;
import com.lcy.service.IndexService;
import com.lcy.util.JWTUtils;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    /**
     * 注册前验证用户信息
     * @param params
     * @return
     */
    @Override
    public AjaxResult checkUserInfo(String params) {
        UserEntity userEntity = JSONObject.parseObject(params, UserEntity.class);
        //验证账号
        if(!StringUtils.isEmpty(userEntity.getAccount())){
            String account = userEntity.getAccount();
            int count = indexMapper.checkAccountExist(account);
            if(count == 0){
                return AjaxResult.success();
            }
            return AjaxResult.error();
        }

        return null;
    }

    /**
     * 用户注册
     * @param params
     * @return
     */
    @Override
    public AjaxResult userRegister(String params, HttpServletRequest request, HttpServletResponse response) {
        UserEntity userEntity = JSONObject.parseObject(params, UserEntity.class);
        String id = UUID.randomUUID().toString();
        userEntity.setId(id);
        int userRegisterResult = indexMapper.userRegister(userEntity);
        Map<String,Object> map = new HashMap<>();
        if(userRegisterResult == 1){
            Map<String,String> payload = new HashMap<>();
            payload.put("id", userEntity.getId());
            payload.put("nickname", userEntity.getNickname());
            map.put("token", JWTUtils.getToken(payload));
            return AjaxResult.success(map);
        }
        return AjaxResult.error();
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public AjaxResult userLogin(UserEntity user, HttpServletRequest request) {
        UserEntity userEntity = indexMapper.queryUserByAccountAndPassword(user);
        Map<String,Object> map = new HashMap<>();
        if(Objects.nonNull(userEntity)){
            Map<String,String> payload = new HashMap<>();
            payload.put("id", userEntity.getId());
            payload.put("nickname", userEntity.getNickname());
            payload.put("songlist", userEntity.getSonglist());
            payload.put("songs", userEntity.getSongs());
            map.put("token", JWTUtils.getToken(payload));
            map.put("nickname", userEntity.getNickname());
            return AjaxResult.success(map);
        }else {
            return AjaxResult.error("账号或密码错误，请重试！");
        }
    }
}
