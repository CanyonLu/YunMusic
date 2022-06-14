package com.lcy.nacasController;

import com.lcy.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/user")
public class NacosIndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/userRegister")
    public void userRegister(String params){
        indexService.userRegister(params);
    }
}