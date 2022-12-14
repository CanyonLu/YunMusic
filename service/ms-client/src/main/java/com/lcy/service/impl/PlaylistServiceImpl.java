package com.lcy.service.impl;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import com.lcy.mapper.PlaylistMapper;
import com.lcy.service.PlaylistService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lucy
 * @date Created in 2022/12/13
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Autowired
    private PlaylistMapper playlistMapper;

    /**
     * 获取推荐歌单
     * @return
     */
    @Override
    public AjaxResult getRecommendedPlaylist() {
        Random random = new Random();
        HashSet<String> playlistNumberSet = new HashSet<>();
        while(playlistNumberSet.size() < 8){
            playlistNumberSet.add(String.valueOf(random.nextInt(8) + 1));
        }
        List<String> list = new ArrayList<>();
        list.addAll(playlistNumberSet);
        List<PlaylistEntity> playlist = playlistMapper.getPlaylistByNumberList(list);
        if(playlist != null){
            return AjaxResult.success(playlist);
        }
        return AjaxResult.error();
    }

    /**
     * 获取歌单详情
     * @param playlistNumber
     * @return
     */
    @Override
    public AjaxResult getPlaylistDetail(String playlistNumber) {
        PlaylistEntity playlist = playlistMapper.getPlaylistByNumber(playlistNumber);
        String songsNumber = playlist.getSongsNumber();
        String[] songs = songsNumber.split(",");
        List<String> songList = Arrays.asList(songs);
        List<SongEntity> musics = playlistMapper.getMusicByNumbers(songList);
        playlist.setSonglist(musics);
        return AjaxResult.success(playlist);
    }
}
