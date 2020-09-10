package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.services.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/videos")
public class VideoController {

    public static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @GetMapping(value = "/home")
    @ResponseBody
    public List<Video> getRecentVideos(){
        try {
            return this.videoService.getRecent();
        } catch (Exception e){
            LOGGER.info(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping(value = "/id={video_id}")
    @ResponseBody
    public Video getVideoById(@PathVariable("video_id") Long id){
        try {
            return this.videoService.getVideoById(id);
        } catch (Exception e){
            LOGGER.info(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public Video postNewVideo(@RequestBody Video v){
        try {
            return this.videoService.postNewVideo(v);
        } catch (Exception e){
            LOGGER.info(e.getMessage(), e);
        }
        return null;
    }

    @DeleteMapping(value = "/id={video_id}")
    @ResponseBody
    public Video deleteVideoById(@PathVariable("video_id") Long id){
        try {
            return this.videoService.deleteVideoById(id);
        } catch (Exception e){
            LOGGER.info(e.getMessage(), e);
        }
        return null;
    }
}
