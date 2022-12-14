package com.lcy.mapper;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMapper {
    //获取歌单信息
    PlaylistEntity getPlaylistByNumber(String number);

    List<SongEntity> getMusicByNumbers(List<String> list);

}
