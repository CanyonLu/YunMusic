package com.lcy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongListController {

    @GetMapping("/playlist")
    public String songList(){
        return "playlist/playlist";
    }

    @GetMapping("/playlistDetail")
    public String playlistDetailView(Model mmap, String playlistId){
        mmap.addAttribute("playlistId", playlistId);
        return "playlist/playlistDetail";
    }

    @GetMapping("/playlistWindow")
    public String playlistWindow(){
        return "playlist/playlistWindow";
    }
}
