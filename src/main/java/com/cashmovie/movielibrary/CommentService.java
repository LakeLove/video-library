package com.cashmovie.movielibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CommentService {
  @Autowired
  CommentRepository commentRepository;
  
  public List<Comment> findByVideoId(Long video_id) {
    List<Comment> comments = new ArrayList<>();
    for (Comment comment : findAll()) {
      if (comment.getVideo_id().equals(video_id)) {
        comments.add(comment);
      }
    }
    return comments;
  }
  
  public List<Comment> findAll() {
    return commentRepository.findAll();
  }
  
  public Comment save(Long video_id, Comment comment) {
    comment.setVideo_id(video_id);
    return commentRepository.save(comment);
  }
  
 public void delete(Long video_id, Long comment_id) {
   for (Comment comment : findByVideoId(video_id)) {
     if (comment.getComment_id().equals(comment_id)) {
       commentRepository.delete(comment);
       break;
     }
   }
 }
}
