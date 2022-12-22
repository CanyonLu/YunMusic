package com.lcy.entity;

import lombok.Data;

@Data
public class UserEntity {
    //用户主键
    private String id;
    //用户登录名
    private String account;
    //用户密码
    private String password;
    //昵称
    private String nickname;
    //性别
    private String sex;
    //收藏的歌单
    private String songlist;
    //收藏的歌曲
    private String songs;
}
