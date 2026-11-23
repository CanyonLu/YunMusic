package com.lcy.controller;

import com.lcy.service.SingerService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/singer")
public class SingerController {

    private final SingerService singerService;

    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    /**
     * 歌手列表，gender 为空取全部
     */
    @GetMapping("/getSingerList")
    public AjaxResult getSingerList(String gender) {
        return singerService.getSingerList(gender);
    }

    /**
     * 歌手详情 + 演唱歌曲
     */
    @GetMapping("/getMusicBySinger")
    public AjaxResult getMusicBySinger(String singerId) {
        return singerService.getMusicBySinger(singerId);
    }
}
