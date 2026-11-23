package com.lcy.service;

import com.lcy.entity.SingerEntity;
import com.lcy.entity.SongEntity;
import com.lcy.mapper.SingerMapper;
import com.lcy.util.domain.AjaxResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SingerService {

    private final SingerMapper singerMapper;

    public SingerService(SingerMapper singerMapper) {
        this.singerMapper = singerMapper;
    }

    public AjaxResult getSingerList(String gender) {
        return AjaxResult.success(singerMapper.getSingerList(gender));
    }

    /**
     * 歌手详情 + 演唱歌曲
     */
    public AjaxResult getMusicBySinger(String singerId) {
        if (singerId == null || singerId.trim().isEmpty()) {
            return AjaxResult.error("歌手不存在");
        }
        SingerEntity singer = singerMapper.getSingerDetail(singerId);
        if (singer == null) {
            return AjaxResult.error("歌手不存在");
        }
        List<SongEntity> songs = singerMapper.getMusicBySinger(singer.getName());
        singer.setSonglist(songs == null ? Collections.emptyList() : songs);
        return AjaxResult.success(singer);
    }
}
