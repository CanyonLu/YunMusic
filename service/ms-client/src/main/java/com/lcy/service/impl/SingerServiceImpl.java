package com.lcy.service.impl;

import com.lcy.entity.SingerEntity;
import com.lcy.entity.SongEntity;
import com.lcy.mapper.SingerMapper;
import com.lcy.service.SingerService;
import com.lcy.util.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lucy
 * @date Created in 2022/12/20 23:23
 */
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    /**
     * 获取歌手集合
     * @param gender
     * @return
     */
    @Override
    public AjaxResult getSingerList(String gender) {
        List<SingerEntity> singerList = singerMapper.getSingerList(gender);
        if (singerList != null){
            return AjaxResult.success(singerList);
        }
        return AjaxResult.error();
    }

    /**
     * 根据歌手查歌曲
     * @param singerId
     * @return
     */
    @Override
    public AjaxResult getMusicBySinger(String singerId) {
        SingerEntity singer = singerMapper.getSingerDetail(singerId);
        String singerName = singer.getName();
        List<SongEntity> songList = singerMapper.getMusicBySinger(singerName);
        singer.setSonglist(songList);
        return AjaxResult.success(singer);
    }
}
