package com.lcy.entity;

import lombok.Data;

/**
 * @author lucy
 * @date Created in 2022/12/20 23:23
 */
@Data
public class SingerEntity {
    //歌手主键
    private String id;
    //歌手姓名
    private String name;
    //歌手所演唱歌曲的歌曲id
    private String songIds;
    //歌手的性别 1,男；2,女；3,组合
    private String gender;
    //歌手介绍
    private String intro;
    //代表作
    private String masterpiece;
    //歌手图片真实文件名
    private String photoFileName;
}
