package com.lcy.controiller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class IndexController {

    @RequestMapping("/userRegister")
    @ResponseBody
    public void userRegister(String params){
        System.out.println(params);
    }
}
