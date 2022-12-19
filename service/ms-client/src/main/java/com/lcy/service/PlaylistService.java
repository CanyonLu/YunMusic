package com.lcy.service;

import com.lcy.util.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PlaylistService {

    //获取歌单
    AjaxResult getPlaylist(String style);

    //歌单详情
    AjaxResult getPlaylistDetail(String playlistNumber);

    //获取歌曲
    void getSongData(String number, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
