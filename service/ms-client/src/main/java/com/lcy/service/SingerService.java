package com.lcy.service;

import com.lcy.util.domain.AjaxResult;

public interface SingerService {
    //获取歌手集合
    AjaxResult getSingerList(String gender);
}
