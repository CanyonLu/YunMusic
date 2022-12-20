package com.lcy.entity;

import lombok.Data;

/**
 * @author lucy
 * @date Created in 2022/12/14 20:21
 */
@Data
public class SongEntity {
    //歌曲主键
    private String id;
    //歌曲名
    private String name;
    //歌曲演唱歌手
    private String singer;
    //歌曲所在专辑
    private String album;
    //歌曲路径
    private String url;
}
