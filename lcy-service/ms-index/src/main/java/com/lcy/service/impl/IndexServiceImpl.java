package com.lcy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lcy.entity.UserEntity;
import com.lcy.mapper.IndexMapper;
import com.lcy.service.IndexService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AjaxResult userRegister(String params) {
        UserEntity userEntity = JSONObject.parseObject(params, UserEntity.class);
        String id = UUID.randomUUID().toString();
        userEntity.setId(id);
        int userRegisterResult = indexMapper.userRegister(userEntity);
        if(userRegisterResult == 1){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
