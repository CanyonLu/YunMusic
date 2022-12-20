package com.lcy.entity;

import lombok.Data;

import java.util.List;

/**
 * @author lucy
 * @date Created in 2022/12/14 20:08
 */
@Data
public class PlaylistEntity {

    private String id;

    private String name;

    private String intro;

    private String songIds;

    private String rate;

    private String style;

    private List<SongEntity> songlist;

}
