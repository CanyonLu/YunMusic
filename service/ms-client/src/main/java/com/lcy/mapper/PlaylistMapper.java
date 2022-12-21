package com.lcy.mapper;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistMapper {
    //获取歌单信息
    PlaylistEntity getPlaylistById(String id);

    //获取歌单集合
    List<PlaylistEntity> getPlaylist(@Param("style") String style);

    //获取推荐歌单集合
    List<PlaylistEntity> getRecommendedSongList();

    //根据编号获取歌曲列表
    List<SongEntity> getMusicByIds(List<String> list);

    //根据编号获取歌曲
    SongEntity getMusicById(String id);

}
