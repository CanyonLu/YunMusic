package com.lcy.service;

import com.lcy.util.domain.TableDataInfo;

public interface PlaylistService {

    //歌单列表
    TableDataInfo getPlaylist(String playlistNumber);
}
