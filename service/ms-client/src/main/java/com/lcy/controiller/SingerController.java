package com.lcy.controiller;

import com.lcy.service.SingerService;
import com.lcy.util.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucy
 * @date Created in 2022/12/20 23:18
 */
@RestController
@RequestMapping(value = "/singer")
@RefreshScope
@Slf4j
public class SingerController {

    @Autowired
    private SingerService singerService;

    /**
     * 根据条件获取歌手集合
     * @param gender
     * @return
     */
    @RequestMapping("/getSingerList")
    public AjaxResult getSingerList(String gender){
        return singerService.getSingerList(gender);
    }

    /**
     * 根据歌手id获取歌曲
     * @param singerId
     * @return
     */
    @RequestMapping("/getMusicBySinger")
    public AjaxResult getMusicBySinger(String singerId){
        return singerService.getMusicBySinger(singerId);
    }
}
