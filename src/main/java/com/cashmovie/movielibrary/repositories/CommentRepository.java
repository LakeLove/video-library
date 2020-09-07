package com.cashmovie.movielibrary.repositories;

import com.cashmovie.movielibrary.entities.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
