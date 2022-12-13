package com.lcy.controiller;

import com.alibaba.druid.util.StringUtils;
import com.lcy.service.PlaylistService;
import com.lcy.util.domain.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lucy
 * @date Created in 2022/12/13
 */
@RestController
@RequestMapping(value = "/playlist")
@RefreshScope
@Slf4j
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    /**
     * 获取歌单列表
     * @param playlistNumber
     * @return
     */
    @RequestMapping("/getPlaylist")
    public TableDataInfo getPlaylist(String playlistNumber){
        if(StringUtils.isEmpty(playlistNumber)){
            return new TableDataInfo();
        }else {
            return playlistService.getPlaylist(playlistNumber);
        }
    }
}
