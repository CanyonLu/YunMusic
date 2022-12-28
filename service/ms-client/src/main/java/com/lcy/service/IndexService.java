package com.lcy.service;

import com.lcy.entity.UserEntity;
import com.lcy.util.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IndexService {
    //注册
    AjaxResult userRegister(String params, HttpServletRequest request, HttpServletResponse response);

    //注册前验证用户信息
    AjaxResult checkUserInfo(String params);

    //用户登录
    AjaxResult userLogin(UserEntity userEntity, HttpServletRequest request);

    AjaxResult editUserInfo(UserEntity userEntity);

}
