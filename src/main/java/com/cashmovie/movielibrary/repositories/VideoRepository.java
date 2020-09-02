package com.cashmovie.movielibrary.repositories;

import com.cashmovie.movielibrary.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findTop20ByOrderByIdDesc();
}
