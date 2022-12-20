package com.lcy.service.impl;

import com.lcy.entity.SingerEntity;
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

    @Override
    public AjaxResult getSingerList(String gender) {
        List<SingerEntity> singerList = singerMapper.getSingerList(gender);
        if (singerList != null){
            return AjaxResult.success(singerList);
        }
        return AjaxResult.error();
    }
}
