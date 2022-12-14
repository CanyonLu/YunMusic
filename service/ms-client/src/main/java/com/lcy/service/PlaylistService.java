package com.lcy.service;

import com.lcy.util.domain.AjaxResult;

public interface PlaylistService {

    //歌单列表
    AjaxResult getPlaylist(String playlistNumber);
}
