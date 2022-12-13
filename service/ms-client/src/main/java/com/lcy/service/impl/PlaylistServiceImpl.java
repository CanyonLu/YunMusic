package com.lcy.service.impl;

import com.lcy.service.PlaylistService;
import com.lcy.util.domain.TableDataInfo;
import org.springframework.stereotype.Service;

/**
 * @author lucy
 * @date Created in 2022/12/13
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    @Override
    public TableDataInfo getPlaylist(String playlistNumber) {
        return new TableDataInfo();
    }
}
