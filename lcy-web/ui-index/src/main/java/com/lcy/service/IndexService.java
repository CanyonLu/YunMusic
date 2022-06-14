package com.lcy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("ms-index")
public interface IndexService {

    @RequestMapping("user/userRegister")
    void userRegister (String params);
}
