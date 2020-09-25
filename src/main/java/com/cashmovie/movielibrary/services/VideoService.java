package com.cashmovie.movielibrary.services;

import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return this.videoRepository.save(v);
    }

    public Video getVideoById(Long id) {
        return this.videoRepository.findById(id).get();
    }

    public Video deleteVideoById(Long id) {
        Video v = this.getVideoById(id);
        this.videoRepository.delete(v);
        return v;
    }

    public List<Video> getVideosByAuthor(String author) {
      List<Video> byAuthor = new ArrayList<>();
      for(Video video : this.videoRepository.findAll()) {
        if(video.getAuthor()
                .equals(author)) {
          byAuthor.add(video);
        }
      }
      return byAuthor;
    }

    public List<Video> search(String term){
      return this.filterSearch(term, this.videoRepository.findAll());
    }

    public List<Video> filterSearch(String term, List<Video> videos) {
      return videos.stream().filter(video -> contains(video, term)).collect(Collectors.toList());
    }

    private boolean contains(Video video, String term){
      // Clean up the input and the comparison
      String cleanTerm = term.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
      String cleanCompare = (video.getTitle() + video.getAuthor())
        .replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

      return cleanCompare.contains(cleanTerm);
    }
}
