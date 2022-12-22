package com.lcy.controiller;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lcy.entity.UserEntity;
import com.lcy.service.IndexService;
import com.lcy.util.JWTUtils;
import com.lcy.util.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@RefreshScope
//@CrossOrigin
@Slf4j
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 注册前验证用户信息
     * @param params
     * @return
     */
    @RequestMapping("/checkUserInfo")
    public AjaxResult checkUserInfo(String params){
        System.out.println(params);
        if(StringUtils.isEmpty(params)){
            return AjaxResult.error();
        }else {
            return indexService.checkUserInfo(params);
        }
    }

    /**
     * 校验用户登录状态
     * @param request
     * @return
     */
    @RequestMapping("/checkUserLoginState")
    public Map checkUserLoginState(HttpServletRequest request){
        String token = request.getHeader("token");
        log.info("验证状态：token：" + token);
        DecodedJWT verify = JWTUtils.verify(token);
        String id = verify.getClaim("id").asString();
        String nickname = verify.getClaim("nickname").asString();
        String songlist = verify.getClaim("songlist").asString();
        String songs = verify.getClaim("songs").asString();
        log.info("用户id：{}", id);
        log.info("用户名: {}", nickname);
        Map map = new HashMap<>();
        map.put("msg","请求成功");
        map.put("id", id);
        map.put("nickname", nickname);
        map.put("songlist", songlist);
        map.put("songs", songs);
        map.put("state", true);
        return map;
    }

    /**
     * 用户注册
     * @param params
     * @return
     */
    @RequestMapping("/userRegister")
    public AjaxResult userRegister(String params, HttpServletRequest request, HttpServletResponse response){
        System.out.println(params);
        if(StringUtils.isEmpty(params)){
            return AjaxResult.error();
        }else {
            return indexService.userRegister(params, request, response);
        }
    }

    /**
     * 用户登录
     * @param userEntity
     * @return
     */
    @RequestMapping("/userLogin")
    public AjaxResult userLogin(UserEntity userEntity, HttpServletRequest request){
        return indexService.userLogin(userEntity, request);
    }
}
