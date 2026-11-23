package com.lcy.controller;

import com.lcy.service.PlaylistService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    /**
     * 获取歌单，style 为空取全部
     */
    @GetMapping("/getPlaylist")
    public AjaxResult getPlaylist(String style) {
        return playlistService.getPlaylist(style);
    }

    /**
     * 获取推荐歌单
     */
    @GetMapping("/getRecommendedSongList")
    public AjaxResult getRecommendedSongList() {
        return playlistService.getRecommendedSongList();
    }

    /**
     * 获取歌单详情（含歌曲列表）
     */
    @GetMapping("/getPlaylistDetail")
    public AjaxResult getPlaylistDetail(String playlistId) {
        return playlistService.getPlaylistDetail(playlistId);
    }

    /**
     * 输出歌曲音频流，支持 Range 断点/拖动
     */
    @GetMapping("/getSongData")
    public void getSongData(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        playlistService.getSongData(id, request, response);
    }
}
