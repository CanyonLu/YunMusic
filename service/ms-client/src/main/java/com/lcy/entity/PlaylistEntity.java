package com.lcy.entity;

import lombok.Data;

import java.util.List;

/**
 * @author lucy
 * @date Created in 2022/12/14 20:08
 */
@Data
public class PlaylistEntity {
    //主键
    private String id;
    //歌单名称
    private String name;
    //歌单简介
    private String intro;
    //歌单包括的歌曲id
    private String songIds;
    //歌单评分
    private String rate;
    //歌单风格
    private String style;
    //歌单封面真实文件名
    private String coverFileName;
    //歌单中歌曲集合
    private List<SongEntity> songlist;

}
