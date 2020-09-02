package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @GetMapping(value = "/home")
    @ResponseBody
    public List<Video> getRecentVideos(){
        return this.videoService.getRecent();
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public Video postNewVideo(@RequestBody Video v){
        return this.videoService.postNewVideo(v);
    }
}
