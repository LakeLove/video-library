package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.services.CommentService;
import com.cashmovie.movielibrary.entities.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CommentController {
  private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
  @Autowired
  CommentService commentService;
  
  @GetMapping (value = "/{video_id}")
  @ResponseBody
  public List<Comment> getVideoComment(@PathVariable ("video_id") Long video_id) {
    try {
      return commentService.findByVideoId(video_id);
    } catch (Exception e) {
      LOGGER.info(e.getMessage(), e);
    }
    return null;
  }
  
  @GetMapping (value = "/all")
  @ResponseBody
  public List<Comment> getAllComments() {
    try {
      return commentService.findAll();
    } catch (Exception e) {
      LOGGER.info(e.getMessage(), e);
    }
    return null;
  }
  
  @PostMapping (value = "/{video_id}")
  @ResponseBody
  public Comment postVideoComment(@PathVariable ("video_id") Long video_id, @RequestBody Comment comment) {
    try {
      return commentService.save(video_id, comment);
    } catch (Exception e) {
      LOGGER.info(e.getMessage(), e);
    }
    return null;
  }
  
  @DeleteMapping (value = "/{video_id}/{comment_id}")
  @ResponseBody
  public void deleteVideoComment(@PathVariable ("video_id") Long video_id, @PathVariable ("comment_id") Long comment_id) {
    try {
      commentService.delete(video_id, comment_id);
    } catch (Exception e) {
      LOGGER.info(e.getMessage(), e);
    }
  }
}
