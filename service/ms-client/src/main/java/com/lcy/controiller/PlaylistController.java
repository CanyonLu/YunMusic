package com.lcy.controiller;

import com.alibaba.druid.util.StringUtils;
import com.lcy.service.PlaylistService;
import com.lcy.util.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
     * 获取推荐歌单
     * @param request
     * @return
     */
    @RequestMapping("/getRecommendedPlaylist")
    public AjaxResult getRecommendedPlaylist(HttpServletRequest request){
        return playlistService.getRecommendedPlaylist();
    }

    /**
     * 获取歌单中歌曲列表
     * @param playlistNumber
     * @return
     */
    @RequestMapping("/getPlaylistDetail")
    public AjaxResult getPlaylistDetail(String playlistNumber){
        if(StringUtils.isEmpty(playlistNumber)){
            return AjaxResult.error();
        }else {
            return playlistService.getPlaylistDetail(playlistNumber);
        }
    }
}
