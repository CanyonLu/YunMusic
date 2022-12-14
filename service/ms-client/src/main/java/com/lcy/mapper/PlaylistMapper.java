package com.lcy.mapper;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMapper {
    //获取歌单信息
    PlaylistEntity getPlaylistByNumber(String number);

    //根据编号获取歌单集合
    List<PlaylistEntity> getPlaylistByNumberList(List<String> list);

    //根据编号获取歌曲
    List<SongEntity> getMusicByNumbers(List<String> list);

}
