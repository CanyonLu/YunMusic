package com.lcy.controiller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class IndexController {

    @RequestMapping("/userRegister")
    @ResponseBody
    public String userRegister(String params){
        System.out.println(params);
        return params;
    }
}
