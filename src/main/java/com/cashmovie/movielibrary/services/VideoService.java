package com.cashmovie.movielibrary.services;

import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class VideoService {

    private VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public List<Video> getRecent() {
        return this.videoRepository.findTop20ByOrderByIdDesc();
    }

    public Video postNewVideo(Video v) {
        // Currently mapped to mp4
        v.setDate(new Timestamp(System.currentTimeMillis()));
        this.videoRepository.save(v);

        v.setFilePath("/videos/" + v.getId() + ".mp4");
        return this.videoRepository.save(v);
    }
}
