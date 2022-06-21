package com.lcy.service;

import com.lcy.entity.UserEntity;
import com.lcy.util.domain.AjaxResult;

public interface IndexService {
    //注册
    AjaxResult userRegister(String params);

    //注册前验证用户信息
    AjaxResult checkUserInfo(String params);

    //用户登录
    AjaxResult userLogin(UserEntity userEntity);

}
