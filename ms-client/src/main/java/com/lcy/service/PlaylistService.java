package com.lcy.service;

import com.lcy.entity.PlaylistEntity;
import com.lcy.entity.SongEntity;
import com.lcy.mapper.PlaylistMapper;
import com.lcy.util.domain.AjaxResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlaylistService {

    private static final int BUFFER_SIZE = 8192;

    private final PlaylistMapper playlistMapper;

    public PlaylistService(PlaylistMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }

    public AjaxResult getPlaylist(String style) {
        return AjaxResult.success(playlistMapper.getPlaylist(style));
    }

    public AjaxResult getRecommendedSongList() {
        return AjaxResult.success(playlistMapper.getRecommendedSongList());
    }

    /**
     * 歌单详情：按 songIds 的顺序返回歌曲，空歌单返回空列表而不是报错
     */
    public AjaxResult getPlaylistDetail(String playlistId) {
        if (isBlank(playlistId)) {
            return AjaxResult.error("歌单不存在");
        }
        PlaylistEntity playlist = playlistMapper.getPlaylistById(playlistId);
        if (playlist == null) {
            return AjaxResult.error("歌单不存在");
        }
        List<String> ids = splitIds(playlist.getSongIds());
        if (ids.isEmpty()) {
            playlist.setSonglist(Collections.emptyList());
            return AjaxResult.success(playlist);
        }
        Map<String, SongEntity> byId = new LinkedHashMap<>();
        for (SongEntity song : playlistMapper.getMusicByIds(ids)) {
            byId.put(song.getId(), song);
        }
        List<SongEntity> ordered = new ArrayList<>(ids.size());
        for (String id : ids) {
            SongEntity song = byId.get(id);
            if (song != null) {
                ordered.add(song);
            }
        }
        playlist.setSonglist(ordered);
        return AjaxResult.success(playlist);
    }

    /**
     * 输出音频流，支持 Range（无 Range 时返回 200 全量，有 Range 时返回 206 分段）
     */
    public void getSongData(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        SongEntity song = isBlank(id) ? null : playlistMapper.getMusicById(id);
        if (song == null || isBlank(song.getUrl())) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "歌曲不存在");
            return;
        }
        File file = new File(song.getUrl());
        if (!file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "音频文件不存在");
            return;
        }

        long length = file.length();
        String range = request.getHeader("Range");
        long start = 0;
        long end = length - 1;
        if (range != null && range.startsWith("bytes=")) {
            long[] parsed = parseRange(range, length);
            if (parsed == null) {
                response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                response.setHeader("Content-Range", "bytes */" + length);
                return;
            }
            start = parsed[0];
            end = parsed[1];
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + length);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Type", "audio/mpeg");
        response.setHeader("Content-Length", String.valueOf(end - start + 1));

        try (RandomAccessFile raf = new RandomAccessFile(file, "r");
             OutputStream os = response.getOutputStream()) {
            raf.seek(start);
            byte[] buffer = new byte[BUFFER_SIZE];
            long remaining = end - start + 1;
            while (remaining > 0) {
                int read = raf.read(buffer, 0, (int) Math.min(BUFFER_SIZE, remaining));
                if (read == -1) {
                    break;
                }
                os.write(buffer, 0, read);
                remaining -= read;
            }
            os.flush();
        }
    }

    /**
     * 解析 "bytes=start-end"，返回 [start, end]；无法满足时返回 null
     */
    private long[] parseRange(String range, long length) {
        String spec = range.substring("bytes=".length());
        int dash = spec.indexOf('-');
        if (dash < 0) {
            return null;
        }
        try {
            String startPart = spec.substring(0, dash).trim();
            String endPart = spec.substring(dash + 1).trim();
            long start = startPart.isEmpty() ? 0 : Long.parseLong(startPart);
            long end = endPart.isEmpty() ? length - 1 : Long.parseLong(endPart);
            if (start < 0 || start >= length || start > end) {
                return null;
            }
            return new long[]{start, Math.min(end, length - 1)};
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<String> splitIds(String songIds) {
        if (isBlank(songIds)) {
            return Collections.emptyList();
        }
        List<String> ids = new ArrayList<>();
        for (String id : Arrays.asList(songIds.split(","))) {
            if (!id.trim().isEmpty()) {
                ids.add(id.trim());
            }
        }
        return ids;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
