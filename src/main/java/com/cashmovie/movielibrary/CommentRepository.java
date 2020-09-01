package com.cashmovie.movielibrary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  void delete(Optional<Comment> byId);
}
