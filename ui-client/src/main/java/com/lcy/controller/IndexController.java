package com.lcy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /** 站点入口；/index 为登录/注册成功后的跳转地址 */
    @GetMapping({"/", "/index"})
    public String index() {
        return "index/index";
    }

    @GetMapping("/login")
    public String login() {
        return "index/login";
    }

    @GetMapping("/register")
    public String register() {
        return "index/register";
    }

    @GetMapping("/home")
    public String home() {
        return "index/home";
    }
}
