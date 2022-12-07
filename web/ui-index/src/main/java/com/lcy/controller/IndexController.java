package com.lcy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/login")
    public String login(){
        return "index/login";
    }

    @GetMapping("/register")
    public String register(){
        return "index/register";
    }

    @GetMapping("/index")
    public String index(){
        return "index/index";
    }

    @GetMapping("/demo")
    public String demo(){
        return "index/demo";
    }

    @GetMapping("/home")
    public String home(){
        return "index/home";
    }
}
