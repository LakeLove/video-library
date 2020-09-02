package com.cashmovie.movielibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CommentController {
  @Autowired
  CommentService commentService;
  
  @GetMapping (value = "/{video_id}")
  @ResponseBody
  public List<Comment> getVideoComment(@PathVariable ("video_id") Long video_id) {
    return commentService.findByVideoId(video_id);
  }
  
  @GetMapping (value = "/all")
  @ResponseBody
  public List<Comment> getAllComments() {
    return commentService.findAll();
  }
  
  @PostMapping (value = "/{video_id}")
  @ResponseBody
  public Comment postVideoComment(@PathVariable ("video_id") Long video_id, @RequestBody Comment comment) {
    return commentService.save(video_id, comment);
  }
  
  @DeleteMapping (value = "/{video_id}/{comment_id}")
  @ResponseBody
  public void deleteVideoComment(@PathVariable ("video_id") Long video_id, @PathVariable ("comment_id") Long comment_id) {
    commentService.delete(video_id, comment_id);
  }
}