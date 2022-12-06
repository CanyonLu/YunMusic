package com.lcy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/demo")
    public String demo(){
        return "demo";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }
}
