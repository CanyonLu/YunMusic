package com.lcy.mapper;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMapper {
    //获取歌单信息
    PlaylistEntity getPlaylistByNumber(String number);

    //获取歌单集合
    List<PlaylistEntity> getPlaylist(@Param("style") String style);

    //根据编号获取歌曲列表
    List<SongEntity> getMusicByNumbers(List<String> list);

    //根据编号获取歌曲
    SongEntity getMusicByNumber(String number);

}
