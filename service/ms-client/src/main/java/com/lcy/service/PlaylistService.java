package com.lcy.service;

import com.lcy.util.domain.AjaxResult;

public interface PlaylistService {

    //获取推荐歌单
    AjaxResult getRecommendedPlaylist();

    //歌单详情
    AjaxResult getPlaylistDetail(String playlistNumber);
}
