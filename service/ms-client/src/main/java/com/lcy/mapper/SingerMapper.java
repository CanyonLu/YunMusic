package com.lcy.mapper;

import com.lcy.entity.SingerEntity;
import com.lcy.entity.SongEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lucy
 * @date Created in 2022/12/20 23:27
 */
@Repository
public interface SingerMapper {
    List<SingerEntity> getSingerList(@Param("gender") String gender);

    List<SongEntity> getMusicBySinger(String singerId);
}
