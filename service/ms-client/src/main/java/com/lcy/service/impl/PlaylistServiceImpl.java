package com.lcy.service.impl;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import com.lcy.mapper.PlaylistMapper;
import com.lcy.service.PlaylistService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
    public AjaxResult getPlaylist(String style) {
        List<PlaylistEntity> playlist = playlistMapper.getPlaylist(style);
        if(playlist != null){
            return AjaxResult.success(playlist);
        }
        return AjaxResult.error();
    }

    /**
     * 获取推荐歌单列表
     * @return
     */
    @Override
    public AjaxResult getRecommendedSongList() {
        List<PlaylistEntity> playlist = playlistMapper.getRecommendedSongList();
        if(playlist != null){
            return AjaxResult.success(playlist);
        }
        return AjaxResult.error();
    }

    /**
     * 获取歌单详情
     * @param playlistId
     * @return
     */
    @Override
    public AjaxResult getPlaylistDetail(String playlistId) {
        PlaylistEntity playlist = playlistMapper.getPlaylistById(playlistId);
        String songIds = playlist.getSongIds();
        String[] songs = songIds.split(",");
        List<String> songList = Arrays.asList(songs);
        List<SongEntity> musics = playlistMapper.getMusicByIds(songList);
        playlist.setSonglist(musics);
        return AjaxResult.success(playlist);
    }

    /**
     * 获取歌曲文件返回前台播放
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    public void getSongData(String id, HttpServletRequest request, HttpServletResponse response) {
        SongEntity music = playlistMapper.getMusicById(id);
        String u = music.getUrl();
        try {
            File file = new File(u);
            String range = request.getHeader("range");
            String rs[] = range.split("\\=");
            range = rs[1].split("\\-")[0];
            int start = Integer.parseInt(range);
            long length = file.length();
            if(start > 0){
                response.setStatus(206);
            }
            response.addHeader("Accept-Ranges","bytes");
            response.addHeader("Content-Length", length + "");
            response.addHeader("Content-Range","bytes " + start + "-" + (length - 1) + "/" + length);
            response.addHeader("Content-Type","audio/mpeg;charset=utf-8");
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            fis.skip(start);
            FileCopyUtils.copy(fis, os);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
