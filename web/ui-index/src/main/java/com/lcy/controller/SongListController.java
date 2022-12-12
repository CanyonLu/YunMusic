package com.lcy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongListController {

    @GetMapping("/playlist")
    public String songList(){
        return "playlist/playlist";
    }

    @GetMapping("/playlistDetail")
    public String playlistDetailView(){
        return "playlist/playlistDetail";
    }
}
