package com.lcy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lucy
 * @date Created in 2022/12/20 10:56
 */
@Controller
public class SingerController {

    @GetMapping("/singer")
    public String songList(){
        return "singer/singer";
    }

    @GetMapping("/singerDetail")
    public String singerDetail(Model mmap, String singerId){
        mmap.addAttribute("singerId", singerId);
        return "singer/singerDetail";
    }
}
