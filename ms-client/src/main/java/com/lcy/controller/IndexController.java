package com.lcy.controller;

import com.lcy.config.JwtAuthInterceptor;
import com.lcy.entity.UserEntity;
import com.lcy.service.IndexService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    /**
     * 注册前校验账号是否可用
     */
    @PostMapping("/checkUserInfo")
    public AjaxResult checkUserInfo(@RequestParam(required = false) String params) {
        return indexService.checkUserInfo(params);
    }

    /**
     * 校验登录状态，未登录/失效时返回 state=false（不抛 401，便于首页匿名浏览）
     */
    @GetMapping("/checkUserLoginState")
    public Map<String, Object> checkUserLoginState(HttpServletRequest request) {
        return indexService.checkUserLoginState(request.getHeader("token"));
    }

    /**
     * 用户注册
     */
    @PostMapping("/userRegister")
    public AjaxResult userRegister(@RequestParam(required = false) String params) {
        return indexService.userRegister(params);
    }

    /**
     * 用户登录
     */
    @PostMapping("/userLogin")
    public AjaxResult userLogin(UserEntity userEntity) {
        return indexService.userLogin(userEntity);
    }

    /**
     * 修改用户信息（收藏歌单/歌曲），id 由拦截器从 token 中取，防止越权改他人数据
     */
    @PostMapping("/editUserInfo")
    public AjaxResult editUserInfo(HttpServletRequest request, UserEntity user) {
        user.setId(JwtAuthInterceptor.currentUserId(request));
        return indexService.editUserInfo(user);
    }
}
