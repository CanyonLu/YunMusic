package com.lcy.service.impl;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import com.lcy.mapper.PlaylistMapper;
import com.lcy.service.PlaylistService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author lucy
 * @date Created in 2022/12/13
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistMapper playlistMapper;

    @Override
    public AjaxResult getPlaylist(String playlistNumber) {
        PlaylistEntity playlist = playlistMapper.getPlaylistByNumber(playlistNumber);
        String songsNumber = playlist.getSongsNumber();
        String[] songs = songsNumber.split(",");
        List<String> songList = Arrays.asList(songs);
        List<SongEntity> musics = playlistMapper.getMusicByNumbers(songList);
        playlist.setSonglist(musics);
        return AjaxResult.success(playlist);
    }
}
