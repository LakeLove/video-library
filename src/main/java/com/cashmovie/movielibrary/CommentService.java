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
      if (comment.getVideo_id() == video_id) {
        comments.add(comment);
      }
    }
    return comments;
  }
  
  public List<Comment> findAll() {
    return commentRepository.findAll();
  }
  
  public Comment save(Comment comment) {
    return commentRepository.save(comment);
  }
  
 public void delete(Long comment_id) {
    commentRepository.delete(commentRepository.findById(comment_id));
 }
}
