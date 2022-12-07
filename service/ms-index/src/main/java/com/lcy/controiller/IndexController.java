package com.lcy.controiller;

import com.alibaba.druid.util.StringUtils;
import com.lcy.entity.UserEntity;
import com.lcy.service.IndexService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
@RefreshScope
@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 注册前验证用户信息
     * @param params
     * @return
     */
    @RequestMapping("/checkUserInfo")
    @ResponseBody
    public AjaxResult checkUserInfo(String params){
        System.out.println(params);
        if(StringUtils.isEmpty(params)){
            return AjaxResult.error();
        }else {
            return indexService.checkUserInfo(params);
        }
    }

    /**
     * 用户注册
     * @param params
     * @return
     */
    @RequestMapping("/userRegister")
    @ResponseBody
    public AjaxResult userRegister(String params, HttpServletRequest request, HttpServletResponse response){
        System.out.println(params);
        if(StringUtils.isEmpty(params)){
            return AjaxResult.error();
        }else {
            return indexService.userRegister(params, request, response);
        }
    }

    /**
     *
     * @param userEntity
     * @return
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public AjaxResult userLogin(UserEntity userEntity){
        return indexService.userLogin(userEntity);
    }
}
