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
import javax.servlet.http.HttpServletResponse;

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
     * 获取歌单
     * @param style 歌单风格,null取所有歌单
     * @return
     */
    @RequestMapping("/getPlaylist")
    public AjaxResult getPlaylist(String style){
        return playlistService.getPlaylist(style);
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

    /**
     *根据歌曲编号获取歌曲
     * @param number
     */
    @RequestMapping("/getSongData")
    public void getSongData(String number, HttpServletRequest request, HttpServletResponse response) throws Exception {
        playlistService.getSongData(number, request, response);
    }
}
